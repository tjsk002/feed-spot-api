package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "REFRESH_TOKEN")
data class RefreshTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "refresh_token", nullable = false, unique = true)
    val refreshToken: String,

    @Column(name = "expiry_date", nullable = false)
    val expiryDate: LocalDateTime,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    val user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = true)
    val admin: AdminsEntity? = null
) {
    enum class Role {
        ADMIN, USER
    }
}