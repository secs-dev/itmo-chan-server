package io.github.secsdev.itmochan.entity

import org.springframework.data.relational.core.mapping.Table

@Table("Topics")
data class Topic(
    val topicId : Long,
    val name : String,
    val description : String,
)

data class TopicDTO(
    val name : String,
    val description: String,
)

