package zendot.storage.file_service.config.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import zendot.storage.file_service.file.DomainBucketModel


@Repository
@RepositoryRestResource( collectionResourceRel = "domainValue", path = "domainValue")
interface DomainBucketRepository:MongoRepository<DomainBucketModel,String> {
    fun findByDomain(key:String):DomainBucketModel?
    fun findBySecret(key:String):DomainBucketModel?
}