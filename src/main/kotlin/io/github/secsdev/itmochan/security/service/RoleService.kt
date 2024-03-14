package io.github.secsdev.itmochan.security.service

import io.github.secsdev.itmochan.entity.Role

interface RoleService {
    fun permissionsToRoles(permissions : Long) : List<Role>
}