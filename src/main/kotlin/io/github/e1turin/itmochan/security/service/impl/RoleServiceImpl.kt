package io.github.e1turin.itmochan.security.service.impl

import io.github.e1turin.itmochan.entity.Role
import io.github.e1turin.itmochan.repository.RoleRepository
import io.github.e1turin.itmochan.security.service.RoleService
import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
class RoleServiceImpl(
    val roleRepository: RoleRepository,
): RoleService {

    override fun permissionsToRoles(permissions : Long) : List<Role> =
        splitPermissions(permissions)
            .map { roleRepository.findRoleByRoleId(it) }    //TODO Maybe constantly calling db degrades performance
            .mapNotNull { if (it.isPresent) it.get() else null }


    private fun splitPermissions(permissions: Long) : List<Long> =
        permissions
            .toString(2)
            .reversed()
            .mapIndexedNotNull { index, c -> if (c == '1') convertIndexToRoleId(index) else null }

    private fun convertIndexToRoleId(index: Int): Long = 2.0.pow(index).toLong()
}