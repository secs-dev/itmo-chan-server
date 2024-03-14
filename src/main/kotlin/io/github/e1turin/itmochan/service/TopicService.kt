package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Topic
import io.github.e1turin.itmochan.entity.TopicDTO
import io.github.e1turin.itmochan.response.TopicThreads

interface TopicService {
    fun getTopics(): List<Topic>

    fun addTopic(topicDTO: TopicDTO)

    fun getTopic(topicId : Long): TopicThreads

    fun deleteTopic(topicId : Long)
}