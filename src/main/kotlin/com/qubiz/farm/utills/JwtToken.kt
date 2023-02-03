package com.qubiz.farm.utills

import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.securities.UserPrinciple
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

@Component
class JwtToken {

    val tokenAge = 3600000*24 // 1hour
    @Value("\${jwt-secret}")
    private val signKey: String? = null

    fun generateToken(user: UserPrinciple): String {
        val claims = HashMap<String, Any>()
        val auths = user.authorities?.joinToString(separator = "|")
        val expired = System.currentTimeMillis() + tokenAge
        val subject = "${user.id}:${user.userName}:${user.email}:$auths"
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date())
            .setExpiration(Date(expired))
            .setHeaderParams(mapOf("alg" to "HS512", "typ" to "JWT"))
            .signWith(SignatureAlgorithm.HS512, signKey)
            .compact()
    }

    fun validateToken(token: String, userPrinciple: UserPrinciple): Boolean {
        val userDetails = getUserInfoFromToken(token)
        val isExpired = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).body.expiration.before(Date())
        return (userPrinciple.id == userDetails.id && userPrinciple.userName == userDetails.userName && userPrinciple.email == userDetails.email && !isExpired)
    }

    fun getUserInfoFromToken(token: String): UserPrinciple {
        val claims = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).body
        val userInfos = claims.subject.split(":")
        val id = userInfos[0].toLong()
        val name = userInfos[1]
        val email = userInfos[2]
        val authorities = mutableListOf<GrantedAuthority>()
        userInfos[3].split("|").forEach { authorities.add(SimpleGrantedAuthority(it)) }
        return UserPrinciple(id, name, email, "", authorities)
    }

    fun getUserPrincipleFromRequest(http: HttpServletRequest): UserPrinciple {
        val bearerToken = http.getHeader("Authorization")
        return if (bearerToken.startsWith("Bearer ")) {
            getUserInfoFromToken(bearerToken.substring(7))
        } else getUserInfoFromToken("")
    }
}
