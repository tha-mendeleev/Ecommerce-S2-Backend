package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.Store
import com.qubiz.farm.repos.StoreRepo
import com.qubiz.farm.repos.UserRepo
import com.qubiz.farm.services.StoreService
import com.qubiz.farm.utills.UploadImage
import com.qubiz.farm.utills.UserContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class StoreServiceImpl(
    private val storeRepo: StoreRepo,
    private val userRepo: UserRepo,
    private val userContext: UserContext,
    private val response: Response
    ): StoreService {

    @Value("\${store-image-path}")
    private lateinit var storeDir: String

    @Transactional
    override fun uploadProfileImage(filename: MultipartFile): Map<String, Any> {
        val store = checkStoreOwner()
        if (store.profileImage != null)
            UploadImage.removeImage(store.profileImage!!, storeDir)
        store.profileImage = UploadImage.storeImage(filename, storeDir)
        return response.responseObject(storeRepo.save(store))
    }

    @Transactional
    override fun uploadCoverImage(filename: MultipartFile): Map<String, Any> {
        val store = checkStoreOwner()
        if (store.coverImage != null)
            UploadImage.removeImage(store.coverImage!!, storeDir)
        store.coverImage = UploadImage.storeImage(filename, storeDir)
        return response.responseObject(storeRepo.save(store))
    }

    override fun create(req: Store): Store {
        val owner = userContext.getCurrentUser() ?: throw CustomException(403, "Something went wrong please try again.")
        if (storeRepo.existsByOwnerId(owner.id!!))  throw CustomException(400, "MF, you already had a store!")
        if (storeRepo.existsByName(req.name!!)) throw CustomException(400, "Try different name!")
        val sown = userRepo.findById(owner.id!!).orElseThrow { CustomException(404, "User not found") }
        req.owner = sown
        return storeRepo.save(req)
    }

    override fun update(req: Store): Store {
        val store = storeRepo.findById(req.id!!).orElseThrow { CustomException(404, "Store not found") }
        store.name = req.name
        return storeRepo.save(store)
    }

    override fun remove(req: Long) {
        val store = storeRepo.findById(req).orElseThrow { CustomException(404, "Store not found") }
        storeRepo.delete(store)
    }


    private fun checkStoreOwner(): Store {
        val owner = userContext.getCurrentUser() ?: throw CustomException(403, "Something went wrong please try again.")
        return storeRepo.getStoreByOwnerId(owner.id!!) ?: throw CustomException(400, "MF, you don't have a store!")
    }

}
