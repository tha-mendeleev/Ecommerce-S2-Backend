package com.qubiz.farm.dto

import org.springframework.stereotype.Component

@Component
class Response(private val responseObject: ResponseObject) {

    fun responseObject(obj: Any?, totalElements: Long): Map<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["data"] = obj
            response["total"] = totalElements
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseObject(obj: Any?, totalElements: Long, code: Int?, message: String?): Map<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        val responseObject = ResponseObject(code = code, message = message)
        if (obj != null) {
            response["data"] = obj
            response["total"] = totalElements
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseObject(obj: Any?): Map<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["response"] = responseObject.success()
            response["data"] = obj
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseObject(obj: Any?, code: Int?, message: String?): Map<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        val responseObject = ResponseObject(code = code, message = message)
        if (obj != null) {
            response["response"] = responseObject.success()
            response["data"] = obj
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseCodeWithMessage(code: Int?, message:String): Map<String, Any> {
        val responseObject = ResponseObject(code = code, message = message)
        val response: MutableMap<String, Any> = HashMap()
        response["response"] = responseObject
        return response
    }
}

