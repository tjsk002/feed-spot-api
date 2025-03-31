package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import com.sideproject.userInfo.userInfo.repository.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
        private val usersRepository: UsersRepository
) {
    fun getUserList(): List<UsersEntity> {
        return usersRepository.findAll();
    }

    @Transactional
    fun createUser(): UsersEntity {
        return UsersEntity(1, "test",
                "test", "gender",
                true, "back", "description"
        )
    }
    @Transactional
    fun editUser(): UsersEntity {
        return UsersEntity(1, "test",
                "test", "gender",
                true, "back", "description"
        )
    }
    @Transactional
    fun deleteUser(): UsersEntity {
        return UsersEntity(1, "test",
                "test", "gender",
                true, "back", "description"
        )
    }
}