package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.security.exception.*
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
    @ExceptionHandler(value = [
        DuplicatedUsernameException::class,
    ])
    protected fun handleUserRegisterRestrictions(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(value = [NoSuchRoleException::class])
    protected fun handleServerError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request
        )
    }

    @ExceptionHandler(value = [NoSuchTopicException::class])
    protected fun handleTopicError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }

    @ExceptionHandler(value = [NoSuchCommentException::class])
    protected fun handleCommentError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }

    @ExceptionHandler(value = [NoSuchThreadException::class])
    protected fun handleThreadError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }

    @ExceptionHandler(value = [NoRightsException::class])
    protected fun handleNoRightsError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.FORBIDDEN.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.FORBIDDEN, request
        )
    }

    @ExceptionHandler(value = [StorageException::class, MaxCountFilesException::class, UnsupportableFileTypeException::class])
    protected fun handleStorageError(
        ex: RuntimeException, request: WebRequest,
    ) : ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(value = [FileNotFoundException::class])
    protected fun handleFileNotFoundError(
        ex: RuntimeException, request: WebRequest,
    ) : ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }

    @ExceptionHandler(value = [EmptyAnswersListException::class, UserAlreadyVotedException::class, NoSuchPollException::class])
    protected fun handlePollsError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(value = [NoSuchReactionsException::class])
    protected fun handleNoReactionsError(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = wrapErrorToJson(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }
}
