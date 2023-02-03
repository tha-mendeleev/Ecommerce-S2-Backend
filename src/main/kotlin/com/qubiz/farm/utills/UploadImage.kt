package com.qubiz.farm.utills

import com.qubiz.farm.exceptions.CustomException
import javax.servlet.http.HttpServletRequest
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*

@Component
class UploadImage {

        companion object {

            private const val png = "image/png"
            private const val gif = "image/gif"
            private const val jpeg = "image/jpeg"
            private const val svg = "image/svg+xml"

            fun storeImage(file: MultipartFile, dirPath: String): String {

                val path = Paths.get(dirPath).toAbsolutePath().normalize()
                val directory = File(path.toString())
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                // check if file is empty
                if (file.isEmpty)
                    throw CustomException(400, "Cannot upload empty file. Size: [ ${file.size} ]")

                //check if file is image
                if( !listOf(png, gif, jpeg, svg).contains(file.contentType) ) {
                    println(file.contentType)
                    throw CustomException(400, "File must be an image")
                }
                val originalName = file.originalFilename.toString()
                val extension = originalName.substring(originalName.lastIndexOf("."))
                val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                val newFileName = UUID.randomUUID().toString().plus(formatter.format(Date()).toString()).plus(extension)

                try {
                    Files.copy(file.inputStream, path.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING)
                } catch (e: IOException) {
                    println("Hi exception in storeImage!!")
                    throw CustomException(500, e.message!!)
                } catch (e2: Exception) {
                    throw e2
                }
                return newFileName
            }

            fun removeImage(fileName: String, dirPath: String) {
                try {
                    val path = Paths.get(dirPath).toAbsolutePath().normalize()
                    val filePath = path.resolve(fileName).normalize()
                    val file = File(filePath.toString())
                    if(file.exists()) file.delete()
                } catch (e: IOException){
                    throw CustomException(400, e.message ?: "Remove not success")
                }
            }

            fun loadFileImage(
                fileName: String,
                dirPath: String,
                request: HttpServletRequest
            ): ResponseEntity<Resource> {
                try {
                    val path = Paths.get(dirPath).toAbsolutePath().normalize()
                    val filePath = path.resolve(fileName).normalize()
                    val resource = UrlResource(filePath.toUri())
                    resource.contentLength()
                    println(resource.file.absolutePath)
                    val contentType = request.servletContext.getMimeType(resource.file.absolutePath)
                        ?:
                            throw CustomException(400, "Invalid file type, you punk!")

                    return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource)
                } catch (e: MalformedURLException) {
                    throw CustomException(400, e.message ?: "Something went wrong, you punk!")
                } catch (e: IOException) {
                    throw CustomException(400, "Something went wrong, you punk!")
                }
            }
        }
}
