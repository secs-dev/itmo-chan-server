package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.User
import io.github.e1turin.itmochan.entity.UserRegister

interface UserService {
    fun findUsers() : List<User>

    fun findUserByUsername(username: String) : User

    fun findUserByUserId(userId: Long) : User

    fun save(user : UserRegister)

    fun saveGuest(username: String)

    fun provideUserPermissionsToUser(username: String)
}