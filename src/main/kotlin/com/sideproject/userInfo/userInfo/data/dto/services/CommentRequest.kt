package com.sideproject.userInfo.userInfo.data.dto.services

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentRequest(
    @field:NotBlank(message = "댓글 내용은 필수 입럭 값입니다.")
    @field:Size(min = 1, max = 255, message = "댓글 내용은 255자를 넘을 수 없습니다.")
    var content: String,

    @field:NotBlank(message = "등록할 수 없는 날짜입니다.")
    var targetDate: String,
)