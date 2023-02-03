package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: BaseRepo<User, Long> {
    fun getUserByEmail(email: String): User?
    fun getUserByUsername(userName: String): User?
    fun getUserById(id: Long): User?
    fun existsByEmail(email: String): Boolean
}
