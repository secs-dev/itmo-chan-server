package io.github.e1turin.itmochan.security.service

import io.github.e1turin.itmochan.entity.UserAuth
import io.github.e1turin.itmochan.entity.UserRegister
import io.github.e1turin.itmochan.security.configuration.JWTProperties
import io.github.e1turin.itmochan.security.entity.AuthenticationResponse
import io.github.e1turin.itmochan.security.exception.DuplicatedUsernameException
import io.github.e1turin.itmochan.security.exception.NoSuchUsernameException
import io.github.e1turin.itmochan.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JWTProperties,
    private val userService: UserService,
) {
    fun authentication(userAuth: UserAuth): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userAuth.username,
                userAuth.password
            )
        )
        val user = userDetailsService.loadUserByUsername(userAuth.username)
        val accessToken = createAccessToken(user)
        return AuthenticationResponse(
            user.username,
            accessToken = accessToken,
        )
    }

    fun register(userRegister: UserRegister) : AuthenticationResponse {
        try {
            userService.findUserByUsername(userRegister.username)
            throw DuplicatedUsernameException("Username '${userRegister.username}' is already taken")
        } catch (e : NoSuchUsernameException) {
            userService.save(userRegister)
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    userRegister.username,
                    userRegister.password
                )
            )
            val user = userDetailsService.loadUserByUsername(userRegister.username)
            val accessToken = createAccessToken(user)
            return AuthenticationResponse(
                user.username,
                accessToken = accessToken,
            )
        }
    }
    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )
    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
}