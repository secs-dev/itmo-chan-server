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

data class PollAnswers(
    val pollAnswerId : Int,
    val pollId : Int,
    val answerTitle : String,
    val votesNumber : Int,
)
