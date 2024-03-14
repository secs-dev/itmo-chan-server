package io.github.secsdev.itmochan.service.impl

import io.github.secsdev.itmochan.entity.Role
import io.github.secsdev.itmochan.repository.RoleRepository
import io.github.secsdev.itmochan.exception.NoSuchRoleException
import io.github.secsdev.itmochan.service.RolesService
import org.springframework.stereotype.Service

@Service
class RolesServiceImpl(
    private val repository : RoleRepository,
): RolesService {

    override fun findRoleByName(name: String): Role {
        val role = repository.findRoleByName(name)
        if (role.isEmpty)
            throw NoSuchRoleException("Role '$name' doesn't exists")
        return role.get()
    }
}