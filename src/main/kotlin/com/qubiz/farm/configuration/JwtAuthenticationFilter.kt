package com.qubiz.farm.configuration

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.securities.JwtUserDetailsService
import com.qubiz.farm.utills.JwtToken
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component

class JwtAuthenticationFilter( private val jwtUserDetailsService: JwtUserDetailsService): OncePerRequestFilter() {


    @Autowired
    private lateinit var jwtToken: JwtToken

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String
        val tokenHeader = request.getHeader("Authorization")
        if ((tokenHeader != null) && request.getHeader("Authorization").startsWith("Bearer ")) {
            token = tokenHeader.replace("Bearer ", "")
        } else return filterChain.doFilter(request, response)

        var userId: Long? = null

        try {
            userId = jwtToken.getUserInfoFromToken(token).id
        }
        catch (i: IllegalArgumentException) {
            println("Token not available")
        }
        catch (ex: ExpiredJwtException) {
            println("Token Expired!")
        }
        catch (e: Exception) {
            println("Invalid token")
        }
         if (userId != null) {
            val user = jwtUserDetailsService.loadUserById(userId)
            if (jwtToken.validateToken(token, user)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user.id, null, user.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}
