package com.qubiz.farm.dto

import org.springframework.stereotype.Component

@Component
class ResponseObject(var code : Int? = null, var message: String? = null)  {
    fun success(): ResponseObject = ResponseObject(code ?: 200,  message ?: "Success")
    fun error(): ResponseObject = ResponseObject(code ?: 404, message?: "Empty object")
}
