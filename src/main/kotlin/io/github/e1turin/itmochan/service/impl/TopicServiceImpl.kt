package io.github.e1turin.itmochan.service.impl

import io.github.e1turin.itmochan.entity.TopicDTO
import io.github.e1turin.itmochan.repository.TopicRepository
import io.github.e1turin.itmochan.response.TopicThreads
import io.github.e1turin.itmochan.exception.NoSuchTopicException
import io.github.e1turin.itmochan.service.ThreadService
import io.github.e1turin.itmochan.service.TopicService
import org.springframework.stereotype.Service

@Service
class TopicServiceImpl (
    private val topicRepository : TopicRepository,
    private val threadService: ThreadService,
): TopicService {

    override fun getTopics() = topicRepository.findAll().toList()

    override fun addTopic(topicDTO: TopicDTO) {
        topicRepository.saveTopic(topicDTO.name, topicDTO.description)
    }

    override fun getTopic(topicId : Long): TopicThreads {
        val topicOptional = topicRepository.findTopicByTopicId(topicId)
        if (topicOptional.isEmpty)
            throw NoSuchTopicException("No such topic")
        val topic = topicOptional.get()
        val threads = threadService.getThreadsByTopicId(topicId)
        return TopicThreads(topic, threads)
    }

    override fun deleteTopic(topicId : Long) {
        TODO()
    }
}