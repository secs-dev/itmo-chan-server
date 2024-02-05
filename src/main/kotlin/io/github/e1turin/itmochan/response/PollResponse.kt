package io.github.e1turin.itmochan.response

import io.github.e1turin.itmochan.entity.Poll
import io.github.e1turin.itmochan.entity.PollAnswer

class PollResponse(
    val poll: Poll,
    val answers: List<PollAnswer>,
)
