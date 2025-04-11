package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequest
import com.sideproject.userInfo.userInfo.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}