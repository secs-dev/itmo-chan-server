package io.github.secsdev.itmochan.security.service.impl

import io.github.secsdev.itmochan.entity.UserAuth
import io.github.secsdev.itmochan.entity.UserRegister
import io.github.secsdev.itmochan.security.configuration.JWTProperties
import io.github.secsdev.itmochan.response.AuthenticationResponse
import io.github.secsdev.itmochan.exception.DuplicatedUsernameException
import io.github.secsdev.itmochan.exception.NoSuchUsernameException
import io.github.secsdev.itmochan.security.service.AuthenticationService
import io.github.secsdev.itmochan.security.service.TokenService
import io.github.secsdev.itmochan.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JWTProperties,
    private val userService: UserService,
): AuthenticationService {
    override fun authentication(userAuth: UserAuth): AuthenticationResponse {
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

    override fun register(userRegister: UserRegister) : AuthenticationResponse {
        try {
            userService.findUserByUsername(userRegister.username)
            throw DuplicatedUsernameException("Username '${userRegister.username}' is already taken")
        } catch (e : NoSuchUsernameException) {
            userService.save(userRegister)
            userService.provideUserPermissionsToUser(userRegister.username)
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

    //TODO make through AnonymousAuthenticationToken?
    override fun guest() : AuthenticationResponse {
        val username = UUID.randomUUID().toString()
        try {
            userService.findUserByUsername(username)
            throw DuplicatedUsernameException("Username '${username}' is already taken")
        } catch (e : NoSuchUsernameException) {
            userService.saveGuest(username)
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    username,
                    username,
                )
            )
            val user = userDetailsService.loadUserByUsername(username)
            val accessToken = createAccessToken(user)
            return AuthenticationResponse(
                user.username,
                accessToken = accessToken,
            )
        }
    }
    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration(),
        additionalClaims = mapOf(Pair("authorities", user.authorities.map{it.authority}))
    )
    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
}