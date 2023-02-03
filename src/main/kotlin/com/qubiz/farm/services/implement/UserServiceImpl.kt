package com.qubiz.farm.services.implement

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.User
import com.qubiz.farm.repos.RoleRepo
import com.qubiz.farm.repos.UserRepo
import com.qubiz.farm.services.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val roleRepo: RoleRepo,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun create(user: User, flag: Int) {
        user.roles = null
        val userRole = if (flag == 1) {
           roleRepo.findByName("user") ?: throw CustomException(404, "Role not found")
        } else roleRepo.findByName("seller") ?: throw CustomException(404, "Role not found")
        user.roles = mutableListOf(userRole)
        userRole.users?.add(user)
        user.passwd = passwordEncoder.encode(user.passwd)
        userRepo.save(user)
    }

    override fun loadUserByUserName(uName: String): User? {
        return userRepo.getUserByUsername(uName)
    }

    override fun loadUserByEmail(email: String): User? {
        return userRepo.getUserByEmail(email)
    }

    override fun loadUserById(id: Long): User? {
        return userRepo.getUserById(id)
    }
}
