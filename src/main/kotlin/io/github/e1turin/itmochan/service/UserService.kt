package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.User
import io.github.e1turin.itmochan.entity.UserDTO
import io.github.e1turin.itmochan.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val db : UserRepository,
    private val encoder: PasswordEncoder,
) {
    fun findUsers() : List<User> = db.findAll().toList()

    fun save(user : UserDTO) {
        val updated = user.copy(password = encoder.encode(user.password))
        db.save(updated)
    }
    //fun findUserByUsername(username : String) = db.findUserByUsername(username).orElseThrow(NotFound)
}