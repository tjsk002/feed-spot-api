package com.sideproject.userInfo.userInfo.data.dto.admins

import com.sideproject.userInfo.userInfo.data.entity.AdminEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomAdminDetails(
    private val adminEntity: AdminEntity,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = mutableListOf()
        authorities.add(GrantedAuthority { adminEntity.role })
        return authorities
    }

    override fun getUsername(): String {
        return adminEntity.username
    }

    override fun getPassword(): String {
        return adminEntity.password
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }
}