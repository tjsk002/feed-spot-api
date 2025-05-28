package com.sideproject.userInfo.userInfo.data.dto.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class BoardRequestDto(
    val userId: Long?,

    @field:NotBlank(message = "username is required value.")
    @field:Size(min = 1, max = 255, message = "title is between 1 and 255 characters.")
    val title: String,

    @field:NotBlank(message = "username is required value.")
    @field:Size(min = 1, max = 50000, message = "content is between 1 and 50,000 characters.")
    val content: String,
)