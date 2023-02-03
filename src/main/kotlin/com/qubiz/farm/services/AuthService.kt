package com.qubiz.farm.services

import com.qubiz.farm.dto.request.Register
import javax.servlet.http.HttpServletRequest

interface AuthService {
    fun register(req: Register, flag: Int): Map<String, Any>
    fun refreshToken(http: HttpServletRequest): Map<String, Any>
}
