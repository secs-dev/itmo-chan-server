package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Thread
import io.github.e1turin.itmochan.entity.Topic

data class TopicThreads(
    val topic: Topic,
    val threads: List<Thread>,
)