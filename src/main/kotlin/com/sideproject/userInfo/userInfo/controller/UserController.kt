package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import com.sideproject.userInfo.userInfo.service.UserService
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse.ResponseInfo

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsersList(): RestResponse<List<UsersEntity>> {
        return RestResponse.success(userService. getUserList ())
    }

    @PostMapping
    fun createUser(): ResponseEntity<UsersEntity> {
        return ResponseEntity.ok(userService.createUser())
    }

    @PutMapping
    fun editUser(): ResponseEntity<UsersEntity> {
        return ResponseEntity.ok(userService.editUser())
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<UsersEntity> {
        return ResponseEntity.ok(userService.deleteUser())
    }
}