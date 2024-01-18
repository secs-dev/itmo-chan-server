package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.User
import io.github.e1turin.itmochan.entity.UserDTO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findUserByUsername(username: String) : Optional<User>
    fun save(user : UserDTO)
}