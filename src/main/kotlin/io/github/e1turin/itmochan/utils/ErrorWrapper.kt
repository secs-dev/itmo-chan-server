package io.github.e1turin.itmochan.utils

import io.github.e1turin.itmochan.entity.ErrorResponse
import jakarta.servlet.http.HttpServletResponse

fun includeErrorToHttpResponse(
    code : Int,
    message : String,
    response : HttpServletResponse,
) {
    response.status = code
    response.writer.println(
        convertObjectToJson(ErrorResponse(code, message))
    )
}

fun wrapErrorToJson(
    code : Int,
    message : String,
    ) = convertObjectToJson(ErrorResponse(code, message))