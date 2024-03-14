package io.github.secsdev.itmochan.security.service

import io.github.secsdev.itmochan.entity.UserAuth
import io.github.secsdev.itmochan.entity.UserRegister
import io.github.secsdev.itmochan.response.AuthenticationResponse

interface AuthenticationService {
    fun authentication(userAuth: UserAuth): AuthenticationResponse

    fun register(userRegister: UserRegister) : AuthenticationResponse

    fun guest() : AuthenticationResponse


}