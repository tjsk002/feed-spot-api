package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.services.CommentRequest
import com.sideproject.userInfo.userInfo.data.dto.services.CommentResponseDto
import com.sideproject.userInfo.userInfo.service.AuthService
import com.sideproject.userInfo.userInfo.service.CommentService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class CommentController(
    private val commentService: CommentService,
    private val authService: AuthService,
) {
    @GetMapping("/comments")
    fun getCommentList(
        @PageableDefault(size = 10) pageable: Pageable,
        date: String
    ): ResponseEntity<CommentResponseDto> {
        return ResponseEntity.ok(commentService.getCommentList(pageable, date))
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comments")
    fun createComment(
        @RequestBody @Valid
        data: CommentRequest,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        val userEntity = authService.findByUserName(
            SecurityContextHolder.getContext().authentication.principal.toString()
        )
        return ResponseEntity.ok(commentService.createComment(data, userEntity))
    }
}