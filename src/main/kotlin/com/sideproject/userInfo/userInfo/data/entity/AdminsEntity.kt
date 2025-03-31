package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "ADMINS")
class AdminsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "user_name", length = 100, unique = true, nullable = false)
    var username: String,

    @Column(name = "nick_name", length = 100, nullable = false)
    var nickName: String,

    @Column(length = 50, nullable = false)
    var role: String,

    @Column(length = 100, nullable = false)
    var password: String,
) : BasicEntity() {
}
