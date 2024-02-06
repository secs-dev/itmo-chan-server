package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.PollDTO
import io.github.e1turin.itmochan.repository.PollAnswerRepository
import io.github.e1turin.itmochan.repository.PollRepository
import io.github.e1turin.itmochan.repository.VotedUsersRepository
import io.github.e1turin.itmochan.response.PollResponse
import io.github.e1turin.itmochan.exception.EmptyAnswersListException
import io.github.e1turin.itmochan.exception.NoSuchPollException
import io.github.e1turin.itmochan.exception.UserAlreadyVotedException
import org.springframework.stereotype.Service

@Service
class PollService (
    private val pollRepository: PollRepository,
    private val pollAnswerRepository: PollAnswerRepository,
    private val votedUsersRepository: VotedUsersRepository,
    private val userService: UserService,
) {

    fun createPoll(
        commentId: Long,
        poll: PollDTO?,
    ){
        if (poll == null)
            return
        val pollId = pollRepository.savePoll(commentId, poll.title)
        poll.answers.stream().forEach { pollAnswerRepository.savePollAnswer(pollId, it) }
    }

    fun voteInPoll(
        pollId: Long,
        answersIds: List<Long>,
        username: String,
    ) {
        if (answersIds.isEmpty())
           throw EmptyAnswersListException("You have to choose at least one position")
        val poll = pollRepository.findPollByPollId(pollId)
        if (poll.isEmpty)
            throw NoSuchPollException("No such poll was found")
        val user = userService.findUserByUsername(username)

        val votedUser = votedUsersRepository.findVotedUsersByUserIdAndPollId(user.userId, pollId)
        if (votedUser.isPresent)
            throw UserAlreadyVotedException("You have already voted in this poll")

        pollAnswerRepository.voteInPoll(user.userId, pollId, answersIds)
    }

    fun getPoll(pollId: Long): PollResponse {
        val poll = pollRepository.findPollByPollId(pollId)
        if (poll.isEmpty)
            throw NoSuchPollException("No such poll was found")
        val answers = pollAnswerRepository.findPollAnswersByPollId(pollId)
        if (answers.isEmpty())
            throw EmptyAnswersListException("No answers for this poll was found")
        return PollResponse(poll.get(), answers)
    }

    fun getPollIdByCommentId(commentId: Long): Long {
        val poll = pollRepository.findPollByCommentId(commentId)
        if (poll.isEmpty)
            throw NoSuchPollException("No such poll was found")
        return poll.get().pollId
    }
}