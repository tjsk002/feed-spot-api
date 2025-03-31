package com.sideproject.userInfo.userInfo.repository

import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UsersRepositoryTests {
    @Autowired
    protected lateinit var usersRepo: UsersRepository

    @Autowired
    protected lateinit var adminsRepo: AdminsRepository


    @Test
    protected fun userFindRepoTest() {
        println("test start")
        val totalCount = usersRepo.findAll().size
        assertThat(totalCount).isEqualTo(10)
        println("test end")
    }

    @Test
    protected fun userPostRepoTest() {
        println("test start")
        val newUser: UsersEntity = UsersEntity(
            1, "new user", "nickName", "girl", false, "false", "description",
        )
        val savedUser=usersRepo.save(newUser)
        assertThat(savedUser.userName).isEqualTo("new user")
        println("test end")
    }


}