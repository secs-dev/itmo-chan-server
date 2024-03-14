package io.github.secsdev.itmochan.response

import io.github.secsdev.itmochan.entity.Thread
import io.github.secsdev.itmochan.entity.Topic

data class TopicThreads(
    val topic: Topic,
    val threads: List<Thread>,
)