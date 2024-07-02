package zendot.storage.file_service.file.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zendot.storage.file_service.file.domain.UploadedFile;

import java.util.Optional;

@Repository
public interface UploadedFileRepository extends MongoRepository<UploadedFile, String> {
    Optional<UploadedFile> findById(String fileId);
}