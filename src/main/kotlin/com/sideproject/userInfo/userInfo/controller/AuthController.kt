package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.users.LoginRequest
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequest
import com.sideproject.userInfo.userInfo.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Valid authDto: UserRequest,
    ): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(authService.signUpProcess(authDto))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(authService.loginProcess(loginRequest))
    }

    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") authHeader: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(authService.logoutProcess(authHeader, request, response))
    }
}