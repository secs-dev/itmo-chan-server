package io.github.e1turin.itmochan.security.service

import io.github.e1turin.itmochan.security.configuration.JWTProperties
import io.github.e1turin.itmochan.security.exception.WrongUsernameException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    jwtProperties: JWTProperties
) {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    fun isSameUsername(token: String, userDetails: UserDetails) : Boolean {
        if (userDetails.username != verifyToken(token))
            throw WrongUsernameException("An attempt to use a username other than the one signed in the JWT token")
        return true
    }

    fun verifyToken(token: String) : String? = getAllClaims(token).subject

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()
        return parser
            .parseSignedClaims(token)
            .payload
    }
}

