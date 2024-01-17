package io.github.e1turin.itmochan.entity

data class User(
    val userId : Int,
    val username : String,
    val isuId : Int,
    val roleId : Int?,
    val password : String,
)
