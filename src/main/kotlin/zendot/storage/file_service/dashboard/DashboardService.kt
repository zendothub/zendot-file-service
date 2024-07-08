package zendot.storage.file_service.dashboard

import org.springframework.stereotype.Service
import zendot.storage.file_service.config.repository.DomainBucketRepository
import zendot.storage.file_service.file.DomainBucketModel
import java.security.SecureRandom

@Service
class DashboardService(private  val domainBucketRepository: DomainBucketRepository) {
    fun addDomain(domainBucketModel: DomainBucketModel):DomainBucketModel{
        val response=domainBucketRepository.findByDomain(domainBucketModel.domain)
        if(response!=null){
            throw  Exception("domain already exist")
        }
        return domainBucketRepository.save(domainBucketModel)
    }
}