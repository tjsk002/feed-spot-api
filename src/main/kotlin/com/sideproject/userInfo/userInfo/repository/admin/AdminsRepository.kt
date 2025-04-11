package com.sideproject.userInfo.userInfo.repository.admin

import com.sideproject.userInfo.userInfo.data.entity.AdminsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdminsRepository : JpaRepository<AdminsEntity, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): AdminsEntity?
}