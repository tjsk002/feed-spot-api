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
import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import com.sideproject.userInfo.userInfo.jwt.JwtUtils
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validation
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersRepository: UsersRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val response: HttpServletResponse,
) {
    fun signUpProcess(usersDto: UserRequest): RestResponse<Map<String, String>> {
        if (isExists(usersDto.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.USERNAME_ALREADY_EXISTS)
                )
            )
        }
        usersRepository.save(
            UsersEntity(
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
        val usersEntity = findByUserName(username)
        jwtUtils.userSaveRefreshToken(accessToken, refreshToken, usersEntity)
        response.addHeader("Authorization", "Bearer $accessToken")
        response.addHeader(
            "Set-Cookie",
            "refreshToken=$refreshToken; Path=/; HttpOnly; Secure; SameSite=Strict"
        )

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(
                mapOf(
                    "id" to usersEntity.id,
                    "username" to usersEntity.username,
                    "nickName" to usersEntity.nickName,
                    "role" to usersEntity.role,
                    "createdAt" to usersEntity.createdAt,
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

    private fun findByUserName(username: String): UsersEntity {
        return usersRepository.findByUsername(username) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }
}