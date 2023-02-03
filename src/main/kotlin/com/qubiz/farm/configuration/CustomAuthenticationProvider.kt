package com.qubiz.farm.configuration

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.securities.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider: AuthenticationProvider {

    @Autowired
    private val passwordEncoder = BCryptPasswordEncoder()
    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication == null) throw CustomException(401, "Unauthorized!")
        val email = authentication.principal
        val passwd = authentication.credentials
        val user = jwtUserDetailsService.loadUserByEmail(email.toString())
        val passwdMatched = passwordEncoder.matches(passwd.toString(), user.passwd)
        if (passwdMatched) {
            return UsernamePasswordAuthenticationToken(user.id, null, user.authorities)
        }
        else
            throw CustomException(401, "Incorrect email or password")
    }

    override fun supports(authentication: Class<*>?): Boolean {
       return authentication?.equals(UsernamePasswordAuthenticationToken::class) ?: false
    }
}
