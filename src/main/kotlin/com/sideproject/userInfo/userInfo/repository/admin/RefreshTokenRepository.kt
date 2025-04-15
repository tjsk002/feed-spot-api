package com.sideproject.userInfo.userInfo.repository.admin

import com.sideproject.userInfo.userInfo.data.entity.AdminsEntity
import com.sideproject.userInfo.userInfo.data.entity.RefreshTokenEntity
import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {
    fun findByAdmin(admin: AdminsEntity): RefreshTokenEntity?
    fun findByUser(user: UsersEntity): RefreshTokenEntity?
}