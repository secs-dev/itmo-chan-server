package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.VotedUsers
import org.springframework.data.repository.CrudRepository
import java.util.*

interface VotedUsersRepository : CrudRepository<VotedUsers, Long> {

   fun findVotedUsersByUserIdAndPollId(
       userId: Long,
       pollId: Long,
       ): Optional<VotedUsers>
}
