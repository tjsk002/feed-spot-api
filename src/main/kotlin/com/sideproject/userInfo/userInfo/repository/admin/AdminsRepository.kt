package com.sideproject.userInfo.userInfo.repository.admin

import com.sideproject.userInfo.userInfo.data.entity.AdminEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdminsRepository : JpaRepository<AdminEntity, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): AdminEntity?
}