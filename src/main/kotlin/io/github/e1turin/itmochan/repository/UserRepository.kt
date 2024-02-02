package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.User
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.lang.Nullable
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository : CrudRepository<User, Long> {

    fun findUserByUsername(username: String) : Optional<User>

    @Transactional
    @Modifying
    @Query("INSERT INTO \"Users\"(username, isu_id, password) VALUES (:username, :isu_id, :password)")
    fun saveUser(
        @Param("username") username: String,
        @Nullable @Param("isu_id") isuId : Long?,
        @Param("password") password : String,
        )

    @Transactional
    @Modifying
    @Query("UPDATE \"Users\" SET permissions = :permissions where user_id = :user_id")
    fun setPermissions(@Param(value = "user_id") userId: Long, @Param(value = "permissions") phone: Long)
}