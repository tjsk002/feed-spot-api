package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.notice.ViewCountRequest
import com.sideproject.userInfo.userInfo.service.NoticeService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notices")
class NoticeController(
    private val noticeService: NoticeService
) {
    @GetMapping
    fun getNoticeList(
        @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(noticeService.getNoticeList(pageable))
    }

    @GetMapping("/{noticeId}")
    fun getNoticeDetail(
        @PathVariable(required = true) noticeId: Long,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(noticeService.getNoticeDetail(noticeId))
    }

    @PostMapping("/view-count")
    fun increaseViewCount(
        @RequestBody @Valid noticeDto: ViewCountRequest,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(noticeService.increaseViewCount(noticeDto))
    }
}