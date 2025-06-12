package com.sideproject.userInfo.userInfo.controller.admin

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.service.admin.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class AdminMyController(
    private val adminService: AdminService,
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/my")
    fun my(@RequestHeader("Authorization") authHeader: String): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(adminService.myProcess(authHeader))
    }
}
