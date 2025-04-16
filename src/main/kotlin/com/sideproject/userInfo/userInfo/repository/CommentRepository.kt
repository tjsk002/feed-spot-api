package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<CommentEntity, Long>