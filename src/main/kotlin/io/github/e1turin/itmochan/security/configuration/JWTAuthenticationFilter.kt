package io.github.e1turin.itmochan.security.configuration

import io.github.e1turin.itmochan.exception.WrongUsernameException
import io.github.e1turin.itmochan.security.service.CustomUserDetailsService
import io.github.e1turin.itmochan.security.service.TokenService
import io.github.e1turin.itmochan.utils.includeErrorToHttpResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader.isNullOrEmpty() || authHeader.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }
        val jwtToken = authHeader.extractTokenValue()
        try {
            val username = tokenService.verifyToken(jwtToken)
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(username)
                tokenService.isSameUsername(jwtToken, foundUser)
                updateContext(foundUser, request)
                filterChain.doFilter(request, response)
            }
        }
        //TODO move to another filter
        catch(e : ExpiredJwtException) {
            includeErrorToHttpResponse(HttpServletResponse.SC_BAD_REQUEST, "Token has been expired", response)
            e.printStackTrace() //TODO add logger
        }
        catch (e : WrongUsernameException) {
            includeErrorToHttpResponse(HttpServletResponse.SC_BAD_REQUEST, e.message ?: "Different username", response)
            e.printStackTrace() //TODO add logger
        }
        catch (e : JwtException) {
            includeErrorToHttpResponse(HttpServletResponse.SC_BAD_REQUEST, "Invalid token", response)
            e.printStackTrace() //TODO add logger
        }
        catch (e : UsernameNotFoundException) {
            includeErrorToHttpResponse(HttpServletResponse.SC_BAD_REQUEST, e.message ?: "There is no such user", response)
            e.printStackTrace() //TODO add logger
        }
    }
    private fun String?.doesNotContainBearerToken() =
        this == null || !this.startsWith("Bearer ")
    private fun String.extractTokenValue() =
        this.substringAfter("Bearer ")
    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}