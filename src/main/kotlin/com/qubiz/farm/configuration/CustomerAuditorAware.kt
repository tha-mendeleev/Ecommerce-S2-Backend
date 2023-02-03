package com.qubiz.farm.configuration

import com.qubiz.farm.models.domain.User
import org.springframework.data.domain.AuditorAware
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class CustomerAuditorAware: AuditorAware<User> {

    override fun getCurrentAuditor(): Optional<User> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth is AnonymousAuthenticationToken)
            return Optional.empty()
        return Optional.of(User(id = auth.principal as Long))
    }

}
