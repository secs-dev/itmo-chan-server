package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.*
import io.github.e1turin.itmochan.repository.UserRepository
import io.github.e1turin.itmochan.security.exception.NoSuchUsernameException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository : UserRepository,
    private val roleService: RolesService,
    private val encoder: PasswordEncoder,
) {
    fun findUsers() : List<User> = userRepository.findAll().toList()

    fun findUserByUsername(username: String) : User {
        val usernameFound = userRepository.findUserByUsername(username)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("Username '$username' doesn't exists")
        return usernameFound.get()
    }

    fun save(user : UserRegister) {
        userRepository.saveUser(user.username, user.isuId, encoder.encode(user.password))
    }

    //TODO to think about saving guests
    fun saveGuest(username: String) {
        userRepository.saveUser(username, null, encoder.encode(username))
    }

    fun provideUserPermissionsToUser(username: String) {
        val role = roleService.findRoleByName("USER")
        val user = findUserByUsername(username)
        userRepository.setPermissions(user.userId, user.permissions + role.roleId)
    }
}