package io.github.e1turin.itmochan.entity

import jakarta.validation.constraints.Size
import org.springframework.data.relational.core.mapping.Table

@Table("Polls")
data class Poll(
    val pollId : Long,
    val commentId : Long,
    val title : String,
)

@Table("Voted_users")
data class VotedUsers(
    val pollId : Long,
    val userId : Long,
)

@Table("Poll_answers")
data class PollAnswer(
    val pollAnswerId : Long,
    val pollId : Long,
    val answerTitle : String,
    val votesNumber : Int,
)

data class PollDTO(
    val title : String,
    @field:Size(min = 1, max = 10)
    val answers : List<String>,
)
