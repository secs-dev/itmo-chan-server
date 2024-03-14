package io.github.secsdev.itmochan.repository

import io.github.secsdev.itmochan.entity.Role
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface RoleRepository : CrudRepository<Role, Long> {

    fun findRoleByRoleId(id: Long) : Optional<Role>

    fun findRoleByName(name: String) : Optional<Role>
}
