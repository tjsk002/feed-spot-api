package com.sideproject.userInfo.userInfo.data.dto.users

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UserRequestDto(
    val userData: UserRequest
)

class UserRequest(
    @field:NotBlank(message = "username is required value.")
    @field:Size(min = 5, max = 12, message = "username is between 5 and 12.")
    val username: String,

    @field:NotBlank(message = "nickName is required value.")
    val nickName: String,

    val password: String,

    @field:Pattern(regexp = "^(female|male)$", message = "gender must be either 'female' or 'male'.")
    val gender: String,

    val isActive: Boolean,

    @field:NotBlank(message = "type is required value")
    @field:Pattern(
        regexp = "^(front|back|dba|infra)$",
        message = "type must be one of the following values: 'front', 'back', 'dba', or 'infra'."
    )
    val type: String,

    val role: String,

    val description: String?
)

data class LoginRequest(
    @field :NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field :Size(min = 5, max = 12, message = "아이디는 5~12자 이여야 합니다.")
    var username: String,

    @field :NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 이여야 합니다.")
    var password: String
)

data class MyInfoRequestDto(
    @field:NotBlank(message = "nickName is required value.")
    val nickName: String,

    val isActive: Boolean,
    val description: String?
)