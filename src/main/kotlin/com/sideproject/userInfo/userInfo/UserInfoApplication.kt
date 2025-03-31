package com.sideproject.userInfo.userInfo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class UserInfoApplication

fun main(args: Array<String>) {
	runApplication<UserInfoApplication>(*args)
}
