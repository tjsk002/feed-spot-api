package com.sideproject.userInfo.userInfo.data.dto.board

import com.sideproject.userInfo.userInfo.data.dto.users.UsersDto
import com.sideproject.userInfo.userInfo.data.entity.BoardEntity
import java.time.LocalDateTime

class BoardResponseDto(
    val list: List<BoardDto>,
    val pageInfo: PageInfoDto
)

class BoardDto(
    val id: Long?,
    val user: UsersDto,
    val title: String,
    val content: String,
    val commentCount: Int,
    val viewCount: Int,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        fun fromEntity(boardEntity: BoardEntity): BoardDto {
            return BoardDto(
                boardEntity.id,
                UsersDto.fromEntity(boardEntity.user),
                boardEntity.title,
                boardEntity.content,
                boardEntity.commentCount,
                boardEntity.viewCount,
                boardEntity.createdAt,
                boardEntity.updatedAt,
            )
        }
    }
}

class PageInfoDto(
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int
)
