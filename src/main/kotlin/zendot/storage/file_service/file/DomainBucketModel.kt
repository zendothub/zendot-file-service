package zendot.storage.file_service.file
import org.springframework.data.mongodb.core.mapping.Document
@Document
data class DomainBucketModel(
    val id: String? = null,
    val domain: String,
    var bucket: String,
    val secret:String,
    val cdnUrl:String
)