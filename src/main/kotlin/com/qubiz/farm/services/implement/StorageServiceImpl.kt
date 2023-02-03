package com.qubiz.farm.services.implement

import com.qubiz.farm.services.StorageService
import com.qubiz.farm.utills.UploadImage
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class StorageServiceImpl: StorageService {

    override fun loadImage(filename: String, httpServletRequest: HttpServletRequest, type: String): Any {
        return UploadImage.loadFileImage(filename, "./resource/$type/", httpServletRequest)
    }

}
