package com.sideproject.userInfo.userInfo.data.entity

import com.sideproject.userInfo.userInfo.data.dto.users.UserRequestDto
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "USERS")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "user_name", unique = true)
    var username: String,
    @Column(name = "nick_name")
    var nickName: String,
    var gender: String,
    @Column(name = "is_active")
    var isActive: Boolean,
    var type: String,
    var role: String,
    var password: String,
    var description: String?,
) : BasicEntity() {

    fun editUser(userRequestDto: UserRequestDto): UserEntity {
        this.username = userRequestDto.userData.username
        this.nickName = userRequestDto.userData.nickName
        this.gender = userRequestDto.userData.gender
        this.isActive = userRequestDto.userData.isActive
        this.type = userRequestDto.userData.type
        this.role = userRequestDto.userData.role
        this.password = BCryptPasswordEncoder().encode(userRequestDto.userData.password)
        this.description = userRequestDto.userData.description
        return this
    }

    companion object {
        fun fromDto(userRequestDto: UserRequestDto): UserEntity {
            return UserEntity(
                username = userRequestDto.userData.username,
                nickName = userRequestDto.userData.nickName,
                gender = userRequestDto.userData.gender,
                isActive = userRequestDto.userData.isActive,
                type = userRequestDto.userData.type,
                role = userRequestDto.userData.role,
                password = BCryptPasswordEncoder().encode(userRequestDto.userData.password),
                description = userRequestDto.userData.description,
            )
        }
    }
}
