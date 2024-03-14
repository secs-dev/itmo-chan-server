package io.github.secsdev.itmochan.response

import io.github.secsdev.itmochan.entity.Poll
import io.github.secsdev.itmochan.entity.PollAnswer

class PollResponse(
    val poll: Poll,
    val answers: List<PollAnswer>,
)
