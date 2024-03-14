package io.github.secsdev.itmochan.security.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JWTProperties(
    val key : String,
    val accessTokenExpiration : Long,
    val refreshTokenExpiration : Long,
)
