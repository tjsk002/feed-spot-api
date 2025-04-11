package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class MovieService(
    @Value("\${spring.kobis.kobis-key}") private val apiKey: String,
) {
    private val OPEN_API_ENDPOINT =
        "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"

    fun getMovieList(date: String): RestResponse<Map<String, Any>> {
        val data = fetchMovieListFromKobis(date)
        return RestResponse.success(ResponseUtils.messageAddMapOfParsing(data))
    }

    private fun fetchMovieListFromKobis(date: String): Map<*, *> {
        val headers = HttpHeaders()
        val entity = HttpEntity<Void>(headers)

        val uri = UriComponentsBuilder.fromUriString(OPEN_API_ENDPOINT)
            .queryParam("key", apiKey)
            .queryParam("targetDt", date)
            .build()
            .toUriString()

        val restTemplate = RestTemplate()

        return try {
            val response: ResponseEntity<Map<*, *>> = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                Map::class.java
            )

            response.body ?: throw RuntimeException("응답 본문이 비어있습니다.")
        } catch (e: Exception) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    ResponseUtils.messageMapOfParsing("영화 목록 API 호출 오류: ${e.message}")
                )
            )
        }
    }
}
