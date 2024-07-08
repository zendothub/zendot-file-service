package zendot.storage.file_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import zendot.storage.file_service.file.DomainBucketModel

@SpringBootApplication
class FileServiceApplication{
	@Bean
	fun repositoryRestConfigurer(): RepositoryRestConfigurer {
		return object : RepositoryRestConfigurer {
			override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration, cors: CorsRegistry) {
				config.exposeIdsFor( DomainBucketModel::class.java)
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<FileServiceApplication>(*args)
}
