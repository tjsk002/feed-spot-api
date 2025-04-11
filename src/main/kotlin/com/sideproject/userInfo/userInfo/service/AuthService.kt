package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.SuccessMessage
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequest
import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersRepository: UsersRepository
) {
    fun signUpProcess(usersDto: UserRequest): RestResponse<Map<String, String>> {
        if (isExists(usersDto.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.USERNAME_ALREADY_EXISTS)
                )
            )
        }
        usersRepository.save(
            UsersEntity(
                username = usersDto.username,
                nickName = usersDto.nickName,
                gender = usersDto.gender,
                isActive = usersDto.isActive,
                type = usersDto.type,
                description = usersDto.description,
            )
        )
        return RestResponse.success(
            ResponseUtils.messageMapOfParsing(SuccessMessage.SUCCESS)
        )
    }

    private fun isExists(username: String): Boolean {
        return usersRepository.existsByUsername(username)
    }
}