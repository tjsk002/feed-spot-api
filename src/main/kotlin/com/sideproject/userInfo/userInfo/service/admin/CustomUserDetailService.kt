package com.sideproject.userInfo.userInfo.service.admin

import CustomUserDetails
import com.sideproject.userInfo.userInfo.data.dto.admins.CustomAdminDetails
import com.sideproject.userInfo.userInfo.repository.admin.AdminsRepository
import com.sideproject.userInfo.userInfo.repository.admin.UsersRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class CustomUserDetailsService(
    private val adminsRepository: AdminsRepository,
    private val usersRepository: UsersRepository

) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = usersRepository.findByUsername(username)
        if (user != null) {
            return CustomUserDetails(user)
        }

        val admin = adminsRepository.findByUsername(username)
        if (admin != null) {
            return CustomAdminDetails(admin)
        }

        throw UsernameNotFoundException("User not found with username: $username")
    }
}
