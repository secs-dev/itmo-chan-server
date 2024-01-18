package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.User
import io.github.e1turin.itmochan.entity.UserDTO
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, Long> {

    fun findUserByUsername(username: String) : Optional<User>
    fun save(user : UserDTO)
}