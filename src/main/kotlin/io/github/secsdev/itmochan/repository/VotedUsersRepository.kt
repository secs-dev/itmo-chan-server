package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.VotedUsers
import org.springframework.data.repository.CrudRepository
import java.util.*

interface VotedUsersRepository : CrudRepository<VotedUsers, Long> {

   fun findVotedUsersByUserIdAndPollId(
       userId: Long,
       pollId: Long,
       ): Optional<VotedUsers>
}
