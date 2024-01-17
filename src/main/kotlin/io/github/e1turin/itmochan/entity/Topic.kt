package io.github.e1turin.itmochan.entity

data class Topic(
    val topicId : Int,
    val name : String,
    val description : String,
)

data class TopicDTO(
    val name : String,
    val description: String,
)

