package com.sideproject.userInfo.userInfo.service.admin

import com.sideproject.userInfo.userInfo.data.dto.admins.CustomAdminDetails
import com.sideproject.userInfo.userInfo.data.entity.AdminsEntity
import com.sideproject.userInfo.userInfo.repository.admin.AdminsRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class CustomUserDetailsService(
    private val adminsRepository: AdminsRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val admin: AdminsEntity = adminsRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("존재하지 않는 관리자 회원")
        return CustomAdminDetails(admin)
    }
}