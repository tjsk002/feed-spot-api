package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.SuccessMessage
import com.sideproject.userInfo.userInfo.data.dto.services.CommentRequest
import com.sideproject.userInfo.userInfo.data.dto.services.CommentResponseDto
import com.sideproject.userInfo.userInfo.data.dto.services.CommentsDto
import com.sideproject.userInfo.userInfo.data.dto.services.PageInfoDto
import com.sideproject.userInfo.userInfo.data.entity.CommentEntity
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.repository.CommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    @Transactional(readOnly = true)
    fun getCommentList(pageable: Pageable, date: String): CommentResponseDto {
        val commentListDto: Page<CommentEntity> = commentRepository.findByTargetDate(LocalDate.parse(date), pageable)
        val comment: List<CommentsDto> = commentListDto.map { commentEntity ->
            CommentsDto.fromEntity(commentEntity)
        }.toList()

        val pageInfo = PageInfoDto(
            date,
            commentListDto.number,
            commentListDto.size,
            commentListDto.totalElements,
            commentListDto.totalPages
        )
        return CommentResponseDto(comment, pageInfo)
    }

    fun createComment(commentRequest: CommentRequest, userEntity: UserEntity): RestResponse<Map<String, Any>> {
        val newComment: CommentEntity = CommentEntity.fromDto(commentRequest, userEntity)
        commentRepository.save(newComment)
        return RestResponse.success(ResponseUtils.messageAddMapOfParsing(SuccessMessage.SUCCESS))
    }
}