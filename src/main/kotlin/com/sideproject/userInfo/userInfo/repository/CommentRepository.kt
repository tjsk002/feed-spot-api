package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.MovieCommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CommentRepository : JpaRepository<MovieCommentEntity, Long> {
    fun findByTargetDate(targetDate: LocalDate, pageable: Pageable): Page<MovieCommentEntity>
}