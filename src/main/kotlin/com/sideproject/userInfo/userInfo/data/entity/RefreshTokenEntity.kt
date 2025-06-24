package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
data class RefreshTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "refresh_token", nullable = false, unique = true)
    var refreshToken: String,

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: LocalDateTime,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    val user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = true)
    val admin: AdminEntity? = null
) : BasicEntity() {
    enum class Role {
        ADMIN, USER
    }

    fun deactivate() {
        if (!this.isActive) throw IllegalStateException("The token is already inactive.")
        this.isActive = false
    }
}