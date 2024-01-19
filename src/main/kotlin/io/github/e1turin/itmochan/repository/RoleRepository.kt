package io.github.e1turin.itmochan.repository

import io.github.e1turin.itmochan.entity.Role
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface RoleRepository : CrudRepository<Role, Long> {

    fun findRoleByRoleId(id: Long) : Optional<Role>

    fun findRoleByName(name: String) : Optional<Role>
}
