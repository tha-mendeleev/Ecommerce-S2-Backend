package com.qubiz.farm.services

import javax.servlet.http.HttpServletRequest

interface StorageService {
    fun loadImage(filename:String, httpServletRequest: HttpServletRequest, type: String): Any
}
