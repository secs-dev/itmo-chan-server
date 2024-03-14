package io.github.secsdev.itmochan.service

import io.github.secsdev.itmochan.entity.Topic
import io.github.secsdev.itmochan.entity.TopicDTO
import io.github.secsdev.itmochan.response.TopicThreads

interface TopicService {
    fun getTopics(): List<Topic>

    fun addTopic(topicDTO: TopicDTO)

    fun getTopic(topicId : Long): TopicThreads

    fun deleteTopic(topicId : Long)
}