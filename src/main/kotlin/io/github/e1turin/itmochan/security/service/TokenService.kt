package io.github.e1turin.itmochan.security.service

import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface TokenService {
    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String

    fun isSameUsername(token: String, userDetails: UserDetails) : Boolean

    fun verifyToken(token: String) : String?
}