package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.SuccessMessage
import com.sideproject.userInfo.userInfo.data.dto.services.CommentRequest
import com.sideproject.userInfo.userInfo.data.entity.CommentEntity
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.repository.CommentRepository
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import jakarta.validation.Valid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun getCommentList(date: String): RestResponse<Map<String, Any>> {
        return RestResponse.success(ResponseUtils.messageAddMapOfParsing(SuccessMessage.SUCCESS))
    }

    fun createComment(commentRequest: CommentRequest, userEntity: UserEntity): RestResponse<Map<String, Any>> {
        val newComment: CommentEntity = CommentEntity.fromDto(commentRequest, userEntity)
        commentRepository.save(newComment)
        return RestResponse.success(ResponseUtils.messageAddMapOfParsing(SuccessMessage.SUCCESS))
    }
}