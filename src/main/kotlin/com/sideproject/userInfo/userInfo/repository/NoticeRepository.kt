package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.NoticeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : JpaRepository<NoticeEntity, Long> {
}