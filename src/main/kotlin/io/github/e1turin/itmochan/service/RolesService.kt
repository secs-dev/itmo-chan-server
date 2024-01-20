package io.github.e1turin.itmochan.service

import io.github.e1turin.itmochan.entity.Role
import io.github.e1turin.itmochan.repository.RoleRepository
import io.github.e1turin.itmochan.security.exception.NoSuchRoleException
import org.springframework.stereotype.Service

@Service
class RolesService(
    private val repository : RoleRepository,
) {

    fun findRoleByName(name: String): Role{
        val role = repository.findRoleByName(name)
        if (role.isEmpty)
            throw NoSuchRoleException("Role '$name' doesn't exists")
        return role.get()
    }
}