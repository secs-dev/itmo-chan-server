package io.github.e1turin.itmochan.security.configuration

import io.github.e1turin.itmochan.utils.includeErrorToHttpResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint


class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        includeErrorToHttpResponse(HttpServletResponse.SC_BAD_REQUEST, "Authorization Error", response)
    }
}