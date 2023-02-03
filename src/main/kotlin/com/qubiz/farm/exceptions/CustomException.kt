package com.qubiz.farm.exceptions

class CustomException(code: Int, message: String): RuntimeException(message) {
    var code: Int = 0
    init {
        this.code = code
    }
}
