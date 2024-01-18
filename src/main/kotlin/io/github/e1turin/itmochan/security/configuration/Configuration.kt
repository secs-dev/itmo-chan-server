package io.github.e1turin.itmochan.security.configuration

import io.github.e1turin.itmochan.repository.UserRepository
import io.github.e1turin.itmochan.security.service.CustomUserDetailsService
import io.github.e1turin.itmochan.security.service.RoleService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JWTProperties::class)
class Configuration {
    @Bean
    fun userDetailsService(
        userRepository: UserRepository,
        roleService: RoleService,
        ): UserDetailsService =
        CustomUserDetailsService(userRepository, roleService)
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
    @Bean
    fun authenticationProvider(
        userRepository: UserRepository,
        roleService: RoleService,
        ): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository, roleService))
                it.setPasswordEncoder(encoder())
            }
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}
