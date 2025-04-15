package com.sideproject.userInfo.userInfo.repository.admin

import com.sideproject.userInfo.userInfo.data.entity.AdminEntity
import com.sideproject.userInfo.userInfo.data.entity.RefreshTokenEntity
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {
    fun findByAdmin(admin: AdminEntity): RefreshTokenEntity?
    fun findByUser(user: UserEntity): RefreshTokenEntity?
}