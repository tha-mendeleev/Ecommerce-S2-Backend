package com.qubiz.farm.services

import com.qubiz.farm.models.domain.User

interface UserService {
    fun create(user: User, flag: Int)
    fun loadUserByUserName(uName: String): User?
    fun loadUserByEmail(email: String): User?
    fun loadUserById(id: Long): User?
}
