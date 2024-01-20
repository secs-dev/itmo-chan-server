package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Thread
import io.github.e1turin.itmochan.entity.TopicDTO
import io.github.e1turin.itmochan.repository.TopicRepository
import io.github.e1turin.itmochan.response.TopicThreads
import io.github.e1turin.itmochan.security.exception.NoSuchTopicException
import org.springframework.stereotype.Service

@Service
class TopicService (
    private val topicRepository : TopicRepository,
    private val threadService: ThreadService,
) {

    fun getTopics() = topicRepository.findAll().toList()

    fun addTopic(topicDTO: TopicDTO) {
        topicRepository.saveTopic(topicDTO.name, topicDTO.description)
    }

    fun getTopic(topicId : Long): TopicThreads {
        val topicOptional = topicRepository.findTopicByTopicId(topicId)
        if (topicOptional.isEmpty)
            throw NoSuchTopicException("No such topic")
        val topic = topicOptional.get()
        val threads = threadService.getThreadsByTopicId(topicId)
        return TopicThreads(topic, threads)
    }

    fun deleteTopic(topicId : Long) {
        TODO()
    }
}