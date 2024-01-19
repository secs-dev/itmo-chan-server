package io.github.e1turin.itmochan.security.entity

data class AuthenticationResponse(
    val username: String,
    val accessToken: String,
)
