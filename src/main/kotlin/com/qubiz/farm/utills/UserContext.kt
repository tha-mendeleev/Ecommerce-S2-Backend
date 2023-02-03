package com.qubiz.farm.utills

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.User
import com.qubiz.farm.repos.UserRepo
import com.qubiz.farm.securities.UserPrinciple
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserContext(private val userRepo: UserRepo) {
    fun getCurrentUser(): User? {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth is AnonymousAuthenticationToken)
            return null
        val id = auth.principal as Long
        return userRepo.findById(id).orElseThrow { CustomException(401, "Unauthorized") }
    }
}
