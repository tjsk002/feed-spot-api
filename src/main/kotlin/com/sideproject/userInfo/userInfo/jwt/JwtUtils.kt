package com.sideproject.userInfo.userInfo.jwt

import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    @Value("\${spring.jwt.secret-key}") private val secret: String
) {
    private val key: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
    private val expirationSeconds = 60 * 60 * 10L // 10시간

    fun getUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("username", String::class.java)
    }

    fun getRole(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String::class.java)
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration().before(Date())
    }

    fun createJwtToken(username: String, role: String): String {
        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expirationSeconds))
            .signWith(key)
            .compact()
    }

    fun validation(token: String): Boolean {
        return false
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        return ""
    }

    // UsernamePasswordAuthenticationToken 데이터 조회
    fun getAuthentication(username: String): Authentication? {
        return null
    }

    // Claims 조회
    private fun getAllClaims(token: String) {

    }
}