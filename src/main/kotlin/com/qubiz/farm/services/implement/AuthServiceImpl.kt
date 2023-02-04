package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.request.Register
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.User
import com.qubiz.farm.repos.UserRepo
import com.qubiz.farm.services.AuthService
import com.qubiz.farm.services.UserService
import com.qubiz.farm.utills.JwtToken
import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepo: UserRepo,
    private val userService: UserService,
    private val response: Response,
    private val jwtToken: JwtToken
    ): AuthService {

    override fun register(req: Register, flag: Int): Map<String, Any> {
        if (userRepo.existsByEmail(req.email))
            throw CustomException(400, "Email already exist")
        userService.create(User(username = req.userName, email = req.email, passwd = req.password), flag)
        return response.responseCodeWithMessage(201, "Register Successfully")
    }

    override fun refreshToken(http: HttpServletRequest): Map<String, Any> {
        val user = jwtToken.getUserPrincipleFromRequest(http)
        val token = jwtToken.generateToken(user)
        return response.responseObject(
            mapOf( "token" to token, "role" to user.authorities?.get(0)?.authority)
        )
    }
}
