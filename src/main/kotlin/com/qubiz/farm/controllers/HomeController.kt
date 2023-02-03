package com.qubiz.farm.controllers

import com.qubiz.farm.dto.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HomeController(private val response: Response) {
    @GetMapping
    fun home(): Any {
        return response.responseCodeWithMessage(200, "Success")
    }
}
