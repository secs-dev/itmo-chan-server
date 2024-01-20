package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Thread
import io.github.e1turin.itmochan.repository.ThreadRepository
import org.springframework.stereotype.Service

@Service
class ThreadService(
    private val threadRepository : ThreadRepository,
) {
    fun getThreadsByTopicId(topicId: Long): List<Thread> {
        return threadRepository.findAllByTopicId(topicId)
    }
}