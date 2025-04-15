package com.sideproject.userInfo.userInfo.service.admin

import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.exception.BasicException
import com.sideproject.userInfo.userInfo.data.dto.users.PageInfoDto
import com.sideproject.userInfo.userInfo.data.dto.users.UserRequestDto
import com.sideproject.userInfo.userInfo.data.dto.users.UserResponseDto
import com.sideproject.userInfo.userInfo.data.dto.users.UsersDto
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(
    private val usersRepository: UsersRepository
) {
    @Transactional(readOnly = true)
    fun getUserList(pageable: Pageable): UserResponseDto {
        val userListDto: Page<UserEntity> = usersRepository.findAll(pageable)
        val userContent: List<UsersDto> = userListDto.map { userEntity -> UsersDto.fromEntity(userEntity) }.toList()
        val pageInfo = PageInfoDto(
            userListDto.number,
            userListDto.size,
            userListDto.totalElements,
            userListDto.totalPages
        )
        return UserResponseDto(userContent, pageInfo)
    }

    @Transactional(readOnly = true)
    fun getUserDetail(userId: Long): RestResponse<Map<String, Any>> {
        val findUser = findById(userId)
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(UsersDto.fromEntity(findUser))
        )
    }

    @Transactional
    fun createUser(userRequestDto: UserRequestDto): RestResponse<Map<String, Any>> {
        checkDuplicateUsername(userRequestDto.userData.username)

        val newUser: UserEntity = UserEntity.fromDto(userRequestDto)
        val savedUser: UserEntity = usersRepository.save(newUser)
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(UsersDto.fromEntity(savedUser))
        )
    }

    @Transactional
    fun editUser(userId: Long, userRequestDto: UserRequestDto): RestResponse<Map<String, Any>> {
        val findUser: UserEntity = findById(userId)
        if (userRequestDto.userData.username != findUser.username) {
            checkDuplicateUsername(userRequestDto.userData.username)
        }

        val editedUser: UserEntity = findUser.editUser(userRequestDto)
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(UsersDto.fromEntity(editedUser))
        )
    }

    @Transactional
    fun deleteUser(userId: Long): RestResponse<Map<String, Any>> {
        val findUser: UserEntity = findById(userId)
        findUser.deletedAt = LocalDateTime.now()
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(UsersDto.fromEntity(findUser))
        )
    }

    private fun findById(userId: Long): UserEntity {
        return usersRepository.findByIdOrNull(userId) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun findUserByUserName(username: String): UserEntity {
        return usersRepository.findByUsername(username) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun checkDuplicateUsername(username: String) {
        if (usersRepository.existsByUsername(username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing(
                        ErrorMessage.USER_ALREADY_EXISTS + " " + username
                    )
                )
            )
        }
    }
}