package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*


@Entity
@Table(name = "USERS")
class UsersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(name = "user_name", unique = true)
    var userName: String,
    @Column(name = "nick_name")
    var nickName: String,
    var gender: String,
    @Column(name = "is_active")
    var isActive: Boolean,
    var type: String,
    var description: String,
) : BasicEntity() {

}
