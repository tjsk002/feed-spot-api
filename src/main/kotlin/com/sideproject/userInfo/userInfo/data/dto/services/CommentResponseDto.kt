package com.sideproject.userInfo.userInfo.data.dto.services

import com.sideproject.userInfo.userInfo.data.entity.MovieCommentEntity
import java.time.LocalDateTime

class CommentResponseDto(
    val comment: List<CommentsDto>,
    val pageInfo: PageInfoDto
)

class CommentsDto(
    val userId: Long?,
    val nickName: String,
    val content: String,
    val targetDate: String,
    val createdAt: LocalDateTime? = null,
) {
    companion object {
        fun fromEntity(commentEntity: MovieCommentEntity): CommentsDto {
            return CommentsDto(
                commentEntity.user.id,
                commentEntity.user.nickName,
                commentEntity.content,
                commentEntity.targetDate.toString(),
                commentEntity.createdAt,
            )
        }
    }
}

class PageInfoDto(
    val date: String,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int
)