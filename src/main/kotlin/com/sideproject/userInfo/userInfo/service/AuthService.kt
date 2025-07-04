package com.sideproject.userInfo.userInfo.service

import CustomUserDetails
import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.SuccessMessage
import com.sideproject.userInfo.userInfo.common.response.exception.BasicException
import com.sideproject.userInfo.userInfo.data.dto.users.LoginRequest
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequest
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.jwt.JwtUtils
import com.sideproject.userInfo.userInfo.repository.admin.RefreshTokenRepository
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validation
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

@Service
class AuthService(
    private val usersRepository: UsersRepository,
    private val authenticationManager: AuthenticationManager,
    private val refreshRepository: RefreshTokenRepository,
    private val jwtUtils: JwtUtils,
    private val response: HttpServletResponse,
    @Value("\${spring.jwt.secret-key}") private val secret: String
) {
    private val blacklistedTokens = mutableSetOf<String>()
    private val key: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")

    fun signUpProcess(usersDto: UserRequest): RestResponse<Map<String, String>> {
        if (isExists(usersDto.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.USERNAME_ALREADY_EXISTS)
                )
            )
        }
        usersRepository.save(
            UserEntity(
                username = usersDto.username,
                nickName = usersDto.nickName,
                gender = usersDto.gender,
                password = BCryptPasswordEncoder().encode(usersDto.password),
                isActive = usersDto.isActive,
                type = usersDto.type,
                role = usersDto.role,
                description = usersDto.description,
            )
        )
        return RestResponse.success(
            ResponseUtils.messageMapOfParsing(SuccessMessage.SUCCESS)
        )
    }

    fun loginProcess(loginRequest: LoginRequest): RestResponse<Map<String, Any>> {
        val authentication = attemptAuthentication(loginRequest)
        return successfulAuthentication(authentication)
    }

    private fun isExists(username: String): Boolean {
        return usersRepository.existsByUsername(username)
    }

    private fun successfulAuthentication(authentication: Authentication): RestResponse<Map<String, Any>> {
        val username = (authentication.principal as CustomUserDetails).username
        val role = authentication.authorities.iterator().next().authority
        val accessToken = jwtUtils.createAccessToken(username, role)
        val refreshToken = jwtUtils.createRefreshToken(username, role)
        val userEntity = usersRepository.findByUsername(username)
        jwtUtils.saveRefreshToken(accessToken, refreshToken, null, userEntity)
        response.addHeader("Authorization", "Bearer $accessToken")
        response.addHeader(
            "Set-Cookie",
            "refreshToken=$refreshToken; Path=/; HttpOnly; Secure; SameSite=Strict"
        )

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(
                mapOf(
                    "id" to userEntity?.id,
                    "username" to userEntity?.username,
                    "nickName" to userEntity?.nickName,
                    "role" to userEntity?.role,
                    "createdAt" to userEntity?.createdAt,
                )
            )
        )
    }

    private fun attemptAuthentication(loginRequest: LoginRequest): Authentication {
        val violations = Validation.buildDefaultValidatorFactory().validator.validate(loginRequest)

        if (violations.isNotEmpty()) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.LOGIN_SERVER_ERROR)
                )
            )
        }

        if (!isExists(loginRequest.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.USERNAME_NOT_FOUND)
                )
            )
        }

        try {
            val authenticationToken = UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.password, null
            )

            return authenticationManager.authenticate(authenticationToken)
        } catch (e: BadCredentialsException) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.INCORRECT_PASSWORD)
                )
            )
        } catch (e: Exception) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.LOGIN_SERVER_ERROR)
                )
            )
        }
    }

    fun logoutProcess(
        authHeader: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): RestResponse<Map<String, String>> {
        validateAuthHeader(authHeader)
        val token = authHeader.replace("Bearer ", "")
        val refreshToken = request.cookies?.find { it.name == "refreshToken" }?.value

        if (blacklistedTokens.contains(token)) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.ALREADY_LOGGED_OUT)
                )
            )
        }

        refreshToken?.let {
            val tokenEntity = refreshRepository.findByRefreshToken(refreshToken)
            tokenEntity?.let {
                it.deactivate()
                refreshRepository.save(it)
            }

            val expiredCookie = Cookie("refreshToken", null)
            expiredCookie.path = "/"
            expiredCookie.isHttpOnly = true
            expiredCookie.maxAge = 0
            response.addCookie(expiredCookie)
        }

        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

            blacklistedTokens.add(token)
            return RestResponse.success(
                ResponseUtils.messageMapOfParsing(SuccessMessage.SUCCESS)
            )
        } catch (e: ExpiredJwtException) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(
                        e.message ?: ErrorMessage.TOKEN_EXPIRED
                    )
                )
            )
        } catch (e: Exception) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.INVALID_TOKEN)
                )
            )
        }
    }

    fun findByUserName(username: String): UserEntity {
        return usersRepository.findByUsername(username) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun validateAuthHeader(authHeader: String?) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.NO_AUTHENTICATION_INFORMATION)
                )
            )
        }
    }
}