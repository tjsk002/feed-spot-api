package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.board.BoardRequestDto
import com.sideproject.userInfo.userInfo.data.dto.board.BoardResponseDto
import com.sideproject.userInfo.userInfo.data.dto.board.ViewCountRequest
import com.sideproject.userInfo.userInfo.service.BoardService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/boards")
class BoardController(
    private val boardService: BoardService,
) {
    @GetMapping
    fun getBoardList(
        @PageableDefault(size = 10) pageable: Pageable,
        keyword: String?
    ): ResponseEntity<BoardResponseDto> {
        return ResponseEntity.ok(boardService.getBoardList(pageable, keyword))
    }

    @PostMapping
    fun createBoard(
        @RequestBody @Valid boardDto: BoardRequestDto
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(boardService.createBoard(boardDto))
    }

    @GetMapping("/{boardId}")
    fun getBoardDetail(
        @PathVariable(required = true) boardId: Long,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId))
    }

    @PostMapping("/view-count")
    fun increaseViewCount(
        @RequestBody @Valid boardDto: ViewCountRequest,
    ): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(boardService.increaseViewCount(boardDto))
    }
}