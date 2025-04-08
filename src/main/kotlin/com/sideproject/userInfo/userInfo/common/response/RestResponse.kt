package com.sideproject.userInfo.userInfo.common.response

class RestResponse<T>(
    val resultCode: String,
    val resultMessage: String,
    val resultData: T
) {
    companion object {
        fun <T> success(data: T): RestResponse<T> {
            return RestResponse(
                resultCode = "A200",
                resultMessage = "Success",
                resultData = data
            )
        }

        fun <T> badRequest(data: T): RestResponse<T> {
            return RestResponse(
                resultCode = "A400",
                resultMessage = "Bad Request",
                resultData = data
            )
        }

        fun <T> unauthorized(data: T): RestResponse<T> {
            return RestResponse(
                resultCode = "A401",
                resultMessage = "Unauthorized",
                resultData = data
            )
        }

        fun <T> internalServerError(message: String, data: T): RestResponse<T> {
            return RestResponse(
                resultCode = "A500",
                resultMessage = "Internal Server Error",
                resultData = data
            )
        }
    }
}

data class ValidationErrorResponse(
    val message: String,
    val errors: Map<String, String>
)

object ResponseUtils {
    fun messageMapOfParsing(message: String): Map<String, String> {
        return mapOf("message" to message)
    }

    fun messageAddMapOfParsing(data: Any): Map<String, Any> {
        return mapOf(
            "message" to "success",
            "data" to data
        )
    }
}
