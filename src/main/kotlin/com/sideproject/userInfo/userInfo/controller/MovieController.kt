package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.service.MovieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService
) {
    @GetMapping
    fun getMovieList(date: String): ResponseEntity<RestResponse<Map<String, Any>>> {
        return ResponseEntity.ok(movieService.getMovieList(date))
    }
}