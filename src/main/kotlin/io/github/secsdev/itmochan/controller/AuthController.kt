package io.github.secsdev.itmochan.controller

import io.github.secsdev.itmochan.entity.UserAuth
import io.github.secsdev.itmochan.entity.UserRegister
import io.github.secsdev.itmochan.response.AuthenticationResponse
import io.github.secsdev.itmochan.security.service.AuthenticationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody userAuth: UserAuth
    ): AuthenticationResponse =
        authenticationService.authentication(userAuth)

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid userRegister: UserRegister
    ): AuthenticationResponse =
        authenticationService.register(userRegister)

    @GetMapping("/guest")
    fun guest(): AuthenticationResponse =
        authenticationService.guest()
}
