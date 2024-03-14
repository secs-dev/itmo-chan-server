package io.github.e1turin.itmochan.security.service.impl
import io.github.e1turin.itmochan.repository.UserRepository
import io.github.e1turin.itmochan.security.service.RoleService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

typealias ApplicationUser = io.github.e1turin.itmochan.entity.User

@Service
class CustomUserDetailsServiceImpl(
    val userRepository: UserRepository,
    val roleService: RoleService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findUserByUsername(username).getOrNull()
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("There is no such user in system!")
    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.username)
            .password(this.password)
            .roles(
                *roleService
                    .permissionsToRoles(this.permissions)
                    .map {it.name}
                    .toTypedArray()
            )
            .build()
}
