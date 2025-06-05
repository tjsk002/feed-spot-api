package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Validated
class UserController(
    private val userService: UserService,
) {
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    fun my(
        @RequestHeader("Authorization") authHeader: String,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.myInfo(authHeader))
    }
}