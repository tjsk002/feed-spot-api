package com.sideproject.userInfo.userInfo.common.exception

import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.ValidationErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<RestResponse<ValidationErrorResponse>> {
        val errors =
            ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: ErrorMessage.INVALID_INPUT) }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                RestResponse.badRequest(
                    ValidationErrorResponse(
                        message = ErrorMessage.INVALID_INPUT,
                        errors = errors
                    )
                )
            )
    }

    @ExceptionHandler(CustomBadRequestException::class)
    fun handleBadRequestException(ex: CustomBadRequestException): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getResponse())
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                RestResponse.badRequest(
                    mapOf("message" to ErrorMessage.INVALID_FORMAT)
                )
            )
    }
}