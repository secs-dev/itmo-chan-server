package io.github.e1turin.itmochan.controller

import io.github.e1turin.itmochan.entity.Topic
import io.github.e1turin.itmochan.entity.TopicDTO
import io.github.e1turin.itmochan.response.TopicThreads
import io.github.e1turin.itmochan.service.TopicService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/topic")
class TopicController(
    private val topicService: TopicService
) {

    @GetMapping
    fun getTopics(): List<Topic> {
        return topicService.getTopics()
    }

    @GetMapping("{topicId}")
    fun getTopic(
        @PathVariable("topicId") topicId: Long,
    ): TopicThreads {
        return topicService.getTopic(topicId)
    }


    @PutMapping
    fun addTopic(
        @RequestBody topic: TopicDTO
    ): ResponseEntity<Void> {
        topicService.addTopic(topic)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{topicId}")
    fun deleteTopic(
        @PathVariable("topicId") topicId: Long,
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Todo Deleted")
    }
}