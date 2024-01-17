package io.github.e1turin.itmochan.entity

data class Poll(
    val pollId : Int,
    val commentId : Int,
    val title : String,
)

data class VotedUsers(
    val pollId : Int,
    val userId : Int,
)

data class PollAnswer(
    val pollAnswerId : Int,
    val pollId : Int,
    val answerTitle : String,
    val votesNumber : Int,
)

data class PollDTO(
    val title : String,
)

data class PollAnswerDTO(
    val answerTitle : String,
)
