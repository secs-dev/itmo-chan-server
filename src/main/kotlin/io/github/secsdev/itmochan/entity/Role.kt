package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Roles")
data class Role(
    val roleId : Long,
    val name : String,
    val description : String,
)
