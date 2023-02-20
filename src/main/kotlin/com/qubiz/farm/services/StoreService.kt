package com.qubiz.farm.services

import com.qubiz.farm.base.BaseService
import com.qubiz.farm.models.domain.Store
import com.qubiz.farm.models.domain.User
import org.springframework.web.multipart.MultipartFile

interface StoreService: BaseService<Store, Long> {

    fun getStoreDetailByOwner(): Store
    fun uploadProfileImage(filename: MultipartFile): Map<String, Any>
    fun uploadCoverImage(filename: MultipartFile): Map<String, Any>
}
