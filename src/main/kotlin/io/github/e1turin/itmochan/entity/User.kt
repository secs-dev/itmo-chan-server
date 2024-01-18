package io.github.e1turin.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Users")
data class User(
    val userId : Long,
    val username : String,
    val isuId : Long?,
    val permissions : Long,
    val password : String,
)

data class UserDTO(
    val username : String,
    val isuId : Long?,
    val password : String,
)
