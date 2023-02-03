package com.qubiz.farm.securities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrinciple (
    var id: Long? = null,
    var userName: String,
    var email: String,
    var passwd: String,
    var authorities: MutableList<out GrantedAuthority>? = null
        ) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"))
    }

    override fun getPassword(): String {
        return this.passwd
    }

    override fun getUsername(): String {
        return this.userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
