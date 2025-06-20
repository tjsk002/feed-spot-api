package com.sideproject.userInfo.userInfo.data.dto.notice

import com.sideproject.userInfo.userInfo.data.entity.NoticeEntity
import java.time.LocalDateTime

class NoticeResponseDto(
    val list: List<NoticeDto>,
    val pageInfo: PageInfoDto
)

class NoticeDto(
    val title: String,
    val content: String,
    val viewCount: Int,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    companion object {
        fun fromEntity(noticeEntity: NoticeEntity): NoticeDto {
            return NoticeDto(
                noticeEntity.title,
                noticeEntity.content,
                noticeEntity.viewCount,
                noticeEntity.createdAt,
                noticeEntity.updatedAt,
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