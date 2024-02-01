package io.github.e1turin.itmochan.entity

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
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
    @Size(min = 3, max = 32, message = "Username length should be at least 3 symbols and at maximum 32")
    val username : String,
    @Positive @NotNull
    val isuId : Long?,
    @Size(min = 8, message = "Password length should contains at least 8 symbols")
    val password : String,
)

data class UserAuth(
    val username: String,
    val password : String,
)
