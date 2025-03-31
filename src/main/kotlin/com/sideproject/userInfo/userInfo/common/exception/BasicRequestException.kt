package com.sideproject.userInfo.userInfo.common.exception
import com.sideproject.userInfo.userInfo.common.response.RestResponse

class CustomBadRequestException(private val response: RestResponse<Map<String, String>>) : RuntimeException(response.resultMessage) {
    fun getResponse(): RestResponse<Map<String, String>> = response
}