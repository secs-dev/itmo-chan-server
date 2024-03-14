package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.*
import io.github.secsdev.itmochan.repository.UserRepository
import io.github.secsdev.itmochan.exception.NoSuchUsernameException
import io.github.secsdev.itmochan.service.RolesService
import io.github.secsdev.itmochan.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository : UserRepository,
    private val roleService: RolesService,
    private val encoder: PasswordEncoder,
): UserService {
    override fun findUsers() : List<User> = userRepository.findAll().toList()

    override fun findUserByUsername(username: String) : User {
        val usernameFound = userRepository.findUserByUsername(username)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("Username '$username' doesn't exists")
        return usernameFound.get()
    }

    override fun findUserByUserId(userId: Long) : User {
        val usernameFound = userRepository.findUserByUserId(userId)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("User '$userId' doesn't exists")
        return usernameFound.get()
    }

    override fun save(user : UserRegister) {
        userRepository.saveUser(user.username, user.isuId, encoder.encode(user.password))
    }

    //TODO to think about saving guests
    override fun saveGuest(username: String) {
        userRepository.saveUser(username, null, encoder.encode(username))
    }

    override fun provideUserPermissionsToUser(username: String) {
        val role = roleService.findRoleByName("USER")
        val user = findUserByUsername(username)
        userRepository.setPermissions(user.userId, user.permissions + role.roleId)
    }
}