package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.users.MyInfoRequestDto
import com.sideproject.userInfo.userInfo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Validated
class UserController(
    private val userService: UserService,
) {
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    fun myInfo(
        @RequestHeader("Authorization") authHeader: String,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.myInfo(authHeader))
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/my")
    fun myInfoEdit(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody myInfoRequestDto: MyInfoRequestDto
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.myInfoEdit(authHeader, myInfoRequestDto))
    }
}