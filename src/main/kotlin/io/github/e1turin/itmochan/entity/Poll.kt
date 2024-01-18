package io.github.e1turin.itmochan.entity

data class Poll(
    val pollId : Long,
    val commentId : Long,
    val title : String,
)

data class VotedUsers(
    val pollId : Long,
    val userId : Long,
)

data class PollAnswer(
    val pollAnswerId : Long,
    val pollId : Long,
    val answerTitle : String,
    val votesNumber : Int,
)

data class PollDTO(
    val title : String,
)

data class PollAnswerDTO(
    val answerTitle : String,
)
