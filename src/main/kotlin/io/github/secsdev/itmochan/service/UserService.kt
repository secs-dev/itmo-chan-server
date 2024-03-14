package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.User
import io.github.secsdev.itmochan.entity.UserRegister

interface UserService {
    fun findUsers() : List<User>

    fun findUserByUsername(username: String) : User

    fun findUserByUserId(userId: Long) : User

    fun save(user : UserRegister)

    fun saveGuest(username: String)

    fun provideUserPermissionsToUser(username: String)
}