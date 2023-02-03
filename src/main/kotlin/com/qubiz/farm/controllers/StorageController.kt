package com.qubiz.farm.controllers

import com.qubiz.farm.AppConstants
import com.qubiz.farm.services.StorageService
import javax.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstants.BASE_PATH + "/resource")
class StorageController(private val storageService: StorageService) {

    @GetMapping(
        "/load-image/{type}/{filename:.+}",
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    fun loadImage(@PathVariable filename: String, http: HttpServletRequest, @PathVariable type: String): Any {
        return storageService.loadImage(filename, http, type)
    }
}
