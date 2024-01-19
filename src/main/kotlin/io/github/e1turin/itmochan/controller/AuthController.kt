package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.UserAuth
import io.github.e1turin.itmochan.entity.UserRegister
import io.github.e1turin.itmochan.security.entity.AuthenticationResponse
import io.github.e1turin.itmochan.security.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/login")
    fun authenticate(
        @RequestBody userAuth: UserAuth
    ): AuthenticationResponse =
        authenticationService.authentication(userAuth)

    @RequestMapping("/register")
    fun authenticate(
        @RequestBody userRegister: UserRegister
    ): AuthenticationResponse =
        authenticationService.register(userRegister)
}