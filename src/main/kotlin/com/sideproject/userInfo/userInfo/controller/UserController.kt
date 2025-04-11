package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequestDto
import com.sideproject.userInfo.userInfo.data.dto.users.UserResponseDto
import com.sideproject.userInfo.userInfo.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/users")
class UserController(
    private val userService: UserService
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    fun getUsersList(
        @PageableDefault(size = 10, direction = Sort.Direction.DESC) pageable: Pageable,
    ): RestResponse<UserResponseDto> {
        return RestResponse.success(userService.getUserList(pageable))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    fun getUsersDetail(
        @PathVariable(name = "userId", required = true) userId: Long
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.getUserDetail(userId))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createUser(
        @RequestBody @Valid userRequestDto: UserRequestDto
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.createUser(userRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    fun editUser(
        @PathVariable(name = "userId", required = true) userId: Long,
        @RequestBody userRequestDto: UserRequestDto
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.editUser(userId, userRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable(name = "userId", required = true) userId: Long
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(userService.deleteUser(userId))
    }
}