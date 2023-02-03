package com.qubiz.farm.securities

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUserDetailsServiceImpl(): JwtUserDetailsService {

    @Autowired
    private lateinit var userService: UserService


    // todo implement getUserAuthorities method
    override fun loadUserById(id: Long): UserPrinciple {
        val user = userService.loadUserById(id) ?: return UserPrinciple(null, "0", "0", "0", null)
        val auths: MutableList<GrantedAuthority> = arrayListOf()
        user.roles?.map {
            auths.add(SimpleGrantedAuthority(it.name?.let { role -> "ROLE_${role.uppercase(Locale.getDefault())}"}))
        }
        return UserPrinciple(user.id, user.username!!, user.email!!, user.passwd!!, auths)
    }

    override fun loadUserByEmail(email: String): UserPrinciple {
        val user = userService.loadUserByEmail(email) ?: throw CustomException(404, "Email not found")
        val auths: MutableList<GrantedAuthority> = arrayListOf()
        user.roles?.forEach {
            auths.add(SimpleGrantedAuthority(it.name?.let { role -> "ROLE_${role.uppercase(Locale.getDefault())}"}))
        }
        return UserPrinciple(user.id, user.username!!, user.email!!, user.passwd!!, auths)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.loadUserByEmail(username!!) ?: throw CustomException(401, "User not found")
        val auths: MutableList<GrantedAuthority> = arrayListOf()
        user.roles?.map {
            auths.add(SimpleGrantedAuthority(it.name?.let { role -> "ROLE_${role.uppercase(Locale.getDefault())}"}))
        }
        return UserPrinciple(user.id, user.username!!, user.email!!, user.passwd!!, auths)
    }

}
