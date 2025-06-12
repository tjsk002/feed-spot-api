package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.board.*
import com.sideproject.userInfo.userInfo.data.entity.BoardEntity
import com.sideproject.userInfo.userInfo.repository.BoardRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val authService: AuthService,
) {

    @Transactional(readOnly = true)
    fun getBoardList(pageable: Pageable, keyword: String?): BoardResponseDto {
        val boardListDto: Page<BoardEntity> = if (!keyword.isNullOrBlank()) {
            boardRepository.findByTitleContainingIgnoreCase(keyword, pageable)
        } else {
            boardRepository.findAll(pageable)
        }

        val boardContent: List<BoardDto> = boardListDto.map { BoardDto.fromEntity(it) }.toList()

        val pageInfo = PageInfoDto(
            boardListDto.number,
            boardListDto.size,
            boardListDto.totalElements,
            boardListDto.totalPages
        )

        return BoardResponseDto(boardContent, pageInfo)
    }

    fun createBoard(boardDto: BoardRequestDto): RestResponse<Map<String, Any>> {
        val userEntity = authService.findByUserName(
            SecurityContextHolder.getContext().authentication.principal.toString()
        )

        boardRepository.save(
            BoardEntity(
                user = userEntity,
                title = boardDto.title,
                content = boardDto.content,
            )
        )

        return RestResponse.success(ResponseUtils.messageAddMapOfParsing(boardDto))
    }

    @Transactional(readOnly = true)
    fun getBoardDetail(boardId: Long): RestResponse<Map<String, Any>> {
        val findBoard = findById(boardId)
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(BoardDto.fromEntity(findBoard))
        )
    }

    fun increaseViewCount(boardDto: ViewCountRequest): RestResponse<Map<String, Any>> {
        val board = boardRepository.findById(boardDto.boardId)
            .orElseThrow { IllegalArgumentException("게시글이 존재하지 않습니다.") }

        board.viewCount += 1
        boardRepository.save(board)

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(board)
        )
    }

    private fun findById(boardId: Long): BoardEntity {
        return boardRepository.findByIdOrNull(boardId) ?: throw ChangeSetPersister.NotFoundException()
    }
}