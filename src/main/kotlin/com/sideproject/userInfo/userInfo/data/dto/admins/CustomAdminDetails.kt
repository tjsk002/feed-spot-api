package com.sideproject.userInfo.userInfo.data.dto.admins

import com.sideproject.userInfo.userInfo.data.entity.AdminsEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomAdminDetails(
    private val adminsEntity: AdminsEntity,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = mutableListOf()
        authorities.add(GrantedAuthority { adminsEntity.role })
        return authorities
    }

    override fun getUsername(): String {
        return adminsEntity.username
    }

    override fun getPassword(): String {
        return adminsEntity.password
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