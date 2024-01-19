package io.github.e1turin.itmochan.entity

import io.github.e1turin.itmochan.security.exception.IsuIdNegativeException
import io.github.e1turin.itmochan.security.exception.PasswordMinLengthException
import io.github.e1turin.itmochan.security.exception.UsernameMinLengthException
import org.springframework.data.relational.core.mapping.Table

@Table("Users")
data class User(
    val userId : Long,
    val username : String,
    val isuId : Long?,
    val permissions : Long,
    val password : String,
)

data class UserRegister(
    val username : String,
    val isuId : Long?,
    val password : String,
)

data class UserAuth(
    val username: String,
    val password : String,
)

fun validateUsername(username: String) {

    if (username.length < 3)
        throw UsernameMinLengthException("Min length of username is 3 symbols")
}

fun validateIsuId(isuId: Long?) {
    if (isuId != null && isuId <= 0)
       throw  IsuIdNegativeException("IsuId should be positive number")
}

fun validatePassword(password: String) {
    if (password.length < 8)
        throw PasswordMinLengthException("Password length should contains at least 8 symbols")
}