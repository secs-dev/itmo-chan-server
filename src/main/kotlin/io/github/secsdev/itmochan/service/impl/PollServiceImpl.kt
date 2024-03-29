package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.PollDTO
import io.github.secsdev.itmochan.repository.PollAnswerRepository
import io.github.secsdev.itmochan.repository.PollRepository
import io.github.secsdev.itmochan.repository.VotedUsersRepository
import io.github.secsdev.itmochan.response.PollResponse
import io.github.secsdev.itmochan.exception.EmptyAnswersListException
import io.github.secsdev.itmochan.exception.NoSuchPollException
import io.github.secsdev.itmochan.exception.UserAlreadyVotedException
import io.github.secsdev.itmochan.service.PollService
import io.github.secsdev.itmochan.service.UserService
import org.springframework.stereotype.Service

@Service
class PollServiceImpl (
    private val pollRepository: PollRepository,
    private val pollAnswerRepository: PollAnswerRepository,
    private val votedUsersRepository: VotedUsersRepository,
    private val userService: UserService,
): PollService {

    override fun createPoll(
        commentId: Long,
        poll: PollDTO?,
    ){
        if (poll == null)
            return
        val pollId = pollRepository.savePoll(commentId, poll.title)
        poll.answers.stream().forEach { pollAnswerRepository.savePollAnswer(pollId, it) }
    }

    override fun voteInPoll(
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

    override fun getPoll(pollId: Long): PollResponse {
        val poll = pollRepository.findPollByPollId(pollId)
        if (poll.isEmpty)
            throw NoSuchPollException("No such poll was found")
        val answers = pollAnswerRepository.findPollAnswersByPollId(pollId)
        if (answers.isEmpty())
            throw EmptyAnswersListException("No answers for this poll was found")
        return PollResponse(poll.get(), answers)
    }

    override fun getPollIdByCommentId(commentId: Long): Long {
        val poll = pollRepository.findPollByCommentId(commentId)
        if (poll.isEmpty)
            throw NoSuchPollException("No such poll was found")
        return poll.get().pollId
    }
}