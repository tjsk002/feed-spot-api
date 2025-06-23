package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.service.NoticeService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}