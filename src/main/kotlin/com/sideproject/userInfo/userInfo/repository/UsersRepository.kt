package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<UsersEntity, Long> {
    fun findByUserName(username: String) : UsersEntity?

}