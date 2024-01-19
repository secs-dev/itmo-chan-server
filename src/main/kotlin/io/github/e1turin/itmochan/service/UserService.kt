package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.User
import io.github.e1turin.itmochan.entity.UserRegister
import io.github.e1turin.itmochan.repository.UserRepository
import io.github.e1turin.itmochan.security.exception.NoSuchUsernameException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val db : UserRepository,
    private val encoder: PasswordEncoder,
) {
    fun findUsers() : List<User> = db.findAll().toList()

    fun findUserByUsername(username: String) : User {
        val usernameFound = db.findUserByUsername(username)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("Username '$username' doesn't exists")
        return usernameFound.get()
    }

    fun save(user : UserRegister) {
        db.saveUser(user.username, user.isuId, encoder.encode(user.password))
    }
}