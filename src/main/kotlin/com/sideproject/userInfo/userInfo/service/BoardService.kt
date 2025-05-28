package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.board.BoardDto
import com.sideproject.userInfo.userInfo.data.dto.board.BoardRequestDto
import com.sideproject.userInfo.userInfo.data.dto.board.BoardResponseDto
import com.sideproject.userInfo.userInfo.data.dto.board.PageInfoDto
import com.sideproject.userInfo.userInfo.data.entity.BoardEntity
import com.sideproject.userInfo.userInfo.repository.BoardRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val authService: AuthService,
) {

    @Transactional(readOnly = true)
    fun getBoardList(pageable: Pageable): BoardResponseDto {
        val boardListDto: Page<BoardEntity> = boardRepository.findAll(pageable)
        val boardContent: List<BoardDto> = boardListDto.map { boardEntity -> BoardDto.fromEntity(boardEntity) }.toList()
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
}