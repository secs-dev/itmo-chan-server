package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.security.exception.DuplicatedUsernameException
import io.github.e1turin.itmochan.security.exception.IsuIdNegativeException
import io.github.e1turin.itmochan.security.exception.PasswordMinLengthException
import io.github.e1turin.itmochan.security.exception.UsernameMinLengthException
import io.github.e1turin.itmochan.utils.wrapErrorToJson
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [DuplicatedUsernameException::class])
    protected fun handleDuplicatedUsername(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(value = [UsernameMinLengthException::class, IsuIdNegativeException::class, PasswordMinLengthException::class])
    protected fun handleUserRegisterRestrictions(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }
}
