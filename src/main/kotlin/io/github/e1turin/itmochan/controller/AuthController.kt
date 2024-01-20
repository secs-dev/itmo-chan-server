package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.UserAuth
import io.github.e1turin.itmochan.entity.UserRegister
import io.github.e1turin.itmochan.response.AuthenticationResponse
import io.github.e1turin.itmochan.security.service.AuthenticationService
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
        @RequestBody userRegister: UserRegister
    ): AuthenticationResponse =
        authenticationService.register(userRegister)

    @GetMapping("/guest")
    fun guest(): AuthenticationResponse =
        authenticationService.guest()
}
