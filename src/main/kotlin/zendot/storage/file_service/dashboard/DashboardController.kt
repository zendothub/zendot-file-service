package zendot.storage.file_service.dashboard


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import zendot.storage.file_service.file.DomainBucketModel

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping
class DashboardController(private val dashboardService: DashboardService) {
    @PostMapping("/api/addDomain")
    fun addDomain(@RequestBody domainBucketModel: DomainBucketModel): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(dashboardService.addDomain(domainBucketModel))
        } catch (exception: Exception) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to "Domain already exists"))
        }
    }

    @GetMapping
    fun dashboard(): String {
        return "dashboard"
    }
}