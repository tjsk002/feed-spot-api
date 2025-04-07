package com.sideproject.userInfo.userInfo.common.response

class ErrorMessage {
    companion object {
        // 공통
        const val INVALID_INPUT = "Invalid input"
        const val INVALID_FORMAT = "Invalid format or required fields are missing."

        // 회원 관련
        const val USER_NOT_FOUND = "Username not found"
        const val DUPLICATE_USER = "duplicate username"
        const val USER_ALREADY_EXISTS = "Username already exists"

        // 회원가입 & 계정 관련 에러
        const val USERNAME_ALREADY_EXISTS = "Username already exists"

        // 로그인 관련 에러
        const val USERNAME_NOT_FOUND = "Username not found"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val LOGIN_SERVER_ERROR = "Login failed due to server error"
        const val REFRESH_TOKEN_FAILED = "Refresh token failed"
        const val REFRESH_TOKEN_NOT_FOUND = "Refresh token not found"

        // JWT
        const val TOKEN_EXPIRED = "Token expired"
        const val ACCESS_TOKEN_MISSING = "AccessToken is missing"
        const val ACCESS_TOKEN_NO_NEED_REFRESH = "AccessToken still valid, no need to refresh"
        const val ACCESS_TOKEN_REFRESH = "AccessToken refreshed"
        const val REFRESH_TOKEN_INVALID = "RefreshToken is invalid or expired"

        // 로그아웃 관련 에러
        const val NO_AUTHENTICATION_INFORMATION = "No authentication information"
        const val ALREADY_LOGGED_OUT = "Already logged out"
        const val INVALID_TOKEN = "Invalid token"
    }
}