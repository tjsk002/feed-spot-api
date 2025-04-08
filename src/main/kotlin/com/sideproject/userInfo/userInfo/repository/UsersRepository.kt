package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UsersRepository : JpaRepository<UsersEntity, Long> {
    fun findByUsername(username: String): UsersEntity?
    fun existsByUsername(username: String): Boolean
    fun findAllByDeletedAtIsNull(pageable: Pageable): Page<UsersEntity>
}