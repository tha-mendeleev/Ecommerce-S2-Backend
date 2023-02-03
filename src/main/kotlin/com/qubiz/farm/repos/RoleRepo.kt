package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.Role
import org.springframework.stereotype.Repository

@Repository
interface RoleRepo : BaseRepo<Role, Long> {
    fun findByName(name: String) : Role?
}
