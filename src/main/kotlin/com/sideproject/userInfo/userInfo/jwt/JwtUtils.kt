package com.sideproject.userInfo.userInfo.jwt

import com.sideproject.userInfo.userInfo.data.entity.AdminEntity
import com.sideproject.userInfo.userInfo.data.entity.RefreshTokenEntity
import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import com.sideproject.userInfo.userInfo.repository.admin.RefreshTokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.spec.SecretKeySpec
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@Component
class JwtUtils(
    @Value("\${spring.jwt.secret-key}") private val secret: String,
    @Value("\${spring.jwt.refresh-key}") private val refresh: String,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val accessKey: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
    private val refreshKey: SecretKeySpec = SecretKeySpec(refresh.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")

    val accessExpired = 60.minutes.inWholeMilliseconds
    val refreshExpired = 7.days.inWholeMilliseconds

    fun createAccessToken(username: String, role: String): String {
        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + accessExpired))
            .signWith(accessKey)
            .compact()
    }

    fun createRefreshToken(username: String, role: String): String {
        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + refreshExpired))
            .signWith(refreshKey)
            .compact()
    }

    fun saveRefreshToken(token: String, refreshToken: String, adminEntity: AdminEntity?, userEntity: UserEntity?) {
        val expiryDate = expiryDate()

        adminEntity?.let {
            val existingToken = refreshTokenRepository.findByAdmin(adminEntity)
            if (existingToken != null) {
                existingToken.refreshToken = refreshToken
                existingToken.expiryDate = expiryDate
                existingToken.isActive = true
                refreshTokenRepository.save(existingToken)
            } else {
                val refreshTokenEntity = RefreshTokenEntity(
                    id = null,
                    refreshToken = refreshToken,
                    expiryDate = expiryDate,
                    isActive = true,
                    role = RefreshTokenEntity.Role.ADMIN,
                    admin = adminEntity
                )
                refreshTokenRepository.save(refreshTokenEntity)
            }
        }

        userEntity?.let {
            val existingToken = refreshTokenRepository.findByUser(userEntity)
            if (existingToken != null) {
                existingToken.refreshToken = refreshToken
                existingToken.expiryDate = expiryDate
                existingToken.isActive = true
                refreshTokenRepository.save(existingToken)
            } else {
                val refreshTokenEntity = RefreshTokenEntity(
                    id = null,
                    refreshToken = refreshToken,
                    expiryDate = expiryDate,
                    isActive = true,
                    role = RefreshTokenEntity.Role.USER,
                    user = userEntity
                )
                refreshTokenRepository.save(refreshTokenEntity)
            }
        }
    }

    private fun expiryDate(): LocalDateTime {
        return Instant.now()
            .plusMillis(refreshExpired)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun accessValidation(token: String): Boolean {
        return try {
            val claims = getAccessAllClaims(token)
            !isExpired(claims)
        } catch (e: ExpiredJwtException) {
            println("Access token expired: ${e.message}")
            false
        } catch (e: JwtException) {
            println("Invalid access token: ${e.message}")
            false
        }
    }

    fun isRefreshTokenExpired(token: String): Boolean {
        return try {
            val claims = getRefreshAllClaims(token)
            isExpired(claims)
        } catch (e: ExpiredJwtException) {
            println("Refresh token expired: ${e.message}")
            false
        } catch (e: JwtException) {
            println("Invalid refresh token: ${e.message}")
            false
        }
    }

    fun parseUsername(token: String): String {
        return getAccessAllClaims(token).subject
    }

    // UsernamePasswordAuthenticationToken 데이터 조회
    fun getAuthentication(username: String): Authentication? {
        return null
    }

    fun getAuthenticationFromToken(token: String): UsernamePasswordAuthenticationToken {
        val role = getAccessAllClaims(token)["role", String::class.java]
        val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))

        return UsernamePasswordAuthenticationToken(parseUsername(token), null, authorities)
    }

    fun getAccessAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(accessKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun getRefreshAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(refreshKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun isExpired(claims: Claims): Boolean {
        return claims.expiration.before(Date())
    }
}