package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.UserRequestDto
import com.sideproject.userInfo.userInfo.data.dto.UserResponseDto
import com.sideproject.userInfo.userInfo.data.dto.UsersDto
import com.sideproject.userInfo.userInfo.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
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
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.getUserDetail(userId))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createUser(
        @RequestBody @Valid userRequestDto: UserRequestDto
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.createUser(userRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    fun editUser(
        @PathVariable(name = "userId", required = true) userId: Long,
        @RequestBody userRequestDto: UserRequestDto
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.editUser(userId, userRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable(name = "userId", required = true) userId: Long
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.deleteUser(userId))
    }
}