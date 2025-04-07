package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.ErrorUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.jwt.JwtUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val response: HttpServletResponse,
    private val jwtUtils: JwtUtils,
) {
    fun refreshToken(request: HttpServletRequest): RestResponse<Map<String, String>> {
        val accessToken: String? = request.getHeader("Authorization")?.removePrefix("Bearer ")?.trim()
        val refreshToken = request.cookies
            ?.firstOrNull { it.name == "refreshToken" }
            ?.value ?: return RestResponse.unauthorized(
            ErrorUtils.messageMapOfParsing(ErrorMessage.REFRESH_TOKEN_NOT_FOUND)
        )

        if (accessToken == null) {
            return RestResponse.badRequest(
                ErrorUtils.messageMapOfParsing(ErrorMessage.ACCESS_TOKEN_MISSING)
            )
        }

        if (jwtUtils.accessValidation(accessToken)) {
            return RestResponse.success(
                ErrorUtils.messageMapOfParsing(ErrorMessage.ACCESS_TOKEN_NO_NEED_REFRESH)
            )
        }

        if (jwtUtils.refreshValidation(refreshToken)) {
            try {
                val claims = jwtUtils.getRefreshAllClaims(refreshToken)
                val newAccessToken = jwtUtils.createAccessToken(
                    claims.subject, claims["role", String::class.java]
                )

                response.setHeader("Authorization", "Bearer $newAccessToken")
                val authentication = jwtUtils.getAuthenticationFromToken(newAccessToken)
                SecurityContextHolder.getContext().authentication = authentication
                return RestResponse.success(
                    ErrorUtils.messageMapOfParsing(ErrorMessage.ACCESS_TOKEN_REFRESH)
                )
            } catch (e: Exception) {
                println("refreshToken -- $e")
            }
        } else {
            return RestResponse.badRequest(
                ErrorUtils.messageMapOfParsing(
                    ErrorMessage.REFRESH_TOKEN_INVALID
                )
            )
        }

        return RestResponse.unauthorized(
            ErrorUtils.messageMapOfParsing(
                ErrorMessage.REFRESH_TOKEN_FAILED
            )
        )
    }
}