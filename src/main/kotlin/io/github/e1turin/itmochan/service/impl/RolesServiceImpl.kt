package io.github.e1turin.itmochan.service.impl

import io.github.e1turin.itmochan.entity.Role
import io.github.e1turin.itmochan.repository.RoleRepository
import io.github.e1turin.itmochan.exception.NoSuchRoleException
import io.github.e1turin.itmochan.service.RolesService
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