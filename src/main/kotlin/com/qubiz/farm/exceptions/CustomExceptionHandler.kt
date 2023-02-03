package com.qubiz.farm.exceptions

import com.qubiz.farm.models.response.Response
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun customException(ex: CustomException): ResponseEntity<Any> {
        val message = if ( ex.message == null) "Error not available"
        else ex.message
        return ResponseEntity.status(ex.code).body(Response(ex.code, message!!))
    }
}
