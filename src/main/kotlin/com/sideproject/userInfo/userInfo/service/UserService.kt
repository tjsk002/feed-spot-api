package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.exception.BasicException
import com.sideproject.userInfo.userInfo.data.dto.users.MyInfoRequestDto
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.jwt.JwtUtils
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val jwtUtils: JwtUtils,
    private val usersRepository: UsersRepository,
) {
    fun myInfo(authHeader: String): RestResponse<Map<String, Any>> {
        val userEntity = validateAuthHeader(authHeader)

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(
                mapOf(
                    "username" to userEntity.username,
                    "nickName" to userEntity.nickName,
                    "role" to userEntity.role,
                    "gender" to userEntity.gender,
                    "isActive" to userEntity.isActive,
                    "type" to userEntity.type,
                    "description" to userEntity.description,
                    "createdAt" to userEntity.createdAt,
                    "updatedAt" to userEntity.updatedAt,
                )
            )
        )
    }

    @Transactional
    fun myInfoEdit(authHeader: String, myInfoRequestDto: MyInfoRequestDto): RestResponse<Map<String, Any>> {
        val userEntity: UserEntity = validateAuthHeader(authHeader)
        val editUser: UserEntity = userEntity.myInfoEdit(myInfoRequestDto)

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(editUser)
        )
    }

    private fun findUserByUserName(username: String): UserEntity {
        return usersRepository.findByUsername(username) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun validateAuthHeader(authHeader: String?): UserEntity {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    ResponseUtils.messageMapOfParsing(ErrorMessage.NO_AUTHENTICATION_INFORMATION)
                )
            )
        }

        val token = authHeader.replace("Bearer ", "")
        val username = jwtUtils.parseUsername(token)
        return findUserByUserName(username)
    }
}