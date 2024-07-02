package zendot.storage.file_service.file.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import zendot.storage.file_service.file.domain.S3Path
import zendot.storage.file_service.file.domain.UploadedFile
import zendot.storage.file_service.file.service.FileService


@Service
class FileUtility(
    val fileService: FileService, @Value("\${properties.banner.max_size}") val maxBannerSizeBytes: Int
) {

    val restTemplate = RestTemplate();

    /**
     * Takes url of external object, downloads it, saves to S3 and returns
     * relative path
     */

    /*
    fun imageUrlToRelativePath(
        url: String, fileName: String = url.split("/").last()
    ): UploadedFile.RelativePath {
        val responseEntity: ResponseEntity<Resource> = restTemplate.getForEntity(
            url, Resource::class.java
        )

        // TODO: If image compression is required, uncomment the following
//        if (responseEntity.body?.contentLength()!! > maxBannerSizeBytes) {
//            return fileService.upload(
//                ImageCompressor.compress(responseEntity.body?.inputStream!!),
//                S3Path.BANNER_IMG,
//                fileName
//            ).serializableRelativePath
//        }

        return fileService.upload(
            responseEntity.body?.inputStream, S3Path.MEDIA_IMAGES, fileName
        ).serializableRelativePath
    }
    */

}