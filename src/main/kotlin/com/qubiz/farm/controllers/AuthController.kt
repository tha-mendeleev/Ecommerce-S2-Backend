package com.qubiz.farm.controllers

import com.qubiz.farm.AppConstants
import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.request.AuthRequest
import com.qubiz.farm.dto.request.Register
import com.qubiz.farm.securities.JwtUserDetailsService
import com.qubiz.farm.services.AuthService
import com.qubiz.farm.utills.JwtToken
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppConstants.BASE_PATH + "/auth")
class AuthController(private val authService: AuthService, ) {

    @Autowired
    private lateinit var authenticationManager: AuthenticationProvider
    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService
    @Autowired
    private lateinit var jwtToken: JwtToken
    @Autowired
    private lateinit var response: Response

    @PostMapping("/login")
    fun authenticate(@RequestBody req: AuthRequest): Map<String, Any> {
        val user = jwtUserDetailsService.loadUserByEmail(req.email)
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.email, req.passwd))
        val token = jwtToken.generateToken(user)
        return response.responseObject(
            mapOf(
                "token" to token,
                "role" to "${user.authorities?.map {it.authority}}",
                "user" to mapOf("name" to user.userName, "id" to user.id)
            )
        )
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody req: Register): Map<String, Any> {
        return authService.register(req, 1)
    }

    @PostMapping("/register/seller")
    fun registerSeller(@RequestBody req: Register): Map<String, Any> {
        return authService.register(req, 2)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(http: HttpServletRequest): Map<String, Any> {
        return authService.refreshToken(http)
    }
}
