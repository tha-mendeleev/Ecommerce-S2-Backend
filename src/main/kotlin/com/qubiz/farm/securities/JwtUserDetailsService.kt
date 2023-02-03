package com.qubiz.farm.securities

import org.springframework.security.core.userdetails.UserDetailsService

interface JwtUserDetailsService: UserDetailsService {
    fun loadUserById(id: Long): UserPrinciple
    fun loadUserByEmail(email: String): UserPrinciple
}
