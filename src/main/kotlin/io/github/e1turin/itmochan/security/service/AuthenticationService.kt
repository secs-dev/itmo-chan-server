package io.github.e1turin.itmochan.security.service

import io.github.e1turin.itmochan.entity.UserAuth
import io.github.e1turin.itmochan.entity.UserRegister
import io.github.e1turin.itmochan.response.AuthenticationResponse

interface AuthenticationService {
    fun authentication(userAuth: UserAuth): AuthenticationResponse

    fun register(userRegister: UserRegister) : AuthenticationResponse

    fun guest() : AuthenticationResponse


}