package io.github.e1turin.itmochan.security.service

import io.github.e1turin.itmochan.entity.Role

interface RoleService {
    fun permissionsToRoles(permissions : Long) : List<Role>
}