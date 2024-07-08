package zendot.storage.file_service.file.controller;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import zendot.storage.file_service.file.domain.S3Path;
import zendot.storage.file_service.file.domain.UploadedFile;
import zendot.storage.file_service.file.domain.VideoAndThumbnailResponse;
import zendot.storage.file_service.file.service.FileService;
import zendot.storage.file_service.file.DomainBucketModel;
import java.io.IOException;

@Controller
@RequestMapping("/api/files")
public class FileController {

     private final FileService awsS3Service;
    public FileController(FileService awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping
    public ResponseEntity<UploadedFile> handleFileUpload(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "type") S3Path path,
            HttpServletRequest request
    ) throws IOException {
        String bucket=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DomainBucketModel domainBucketModel) {
            bucket=domainBucketModel.getBucket();
        }
        return new ResponseEntity<>(
                this.awsS3Service.upload(file, path,bucket),
                HttpStatus.OK);
    }

    //below endpoint is used for creating thumbnail from video file and uploading thumbnail in s3
    //it takes video file and s3 path of thumbnail

    @PostMapping("/createThumbnail")
    public ResponseEntity<UploadedFile> createThumbnaiil(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "type") S3Path path,
            HttpServletRequest request
    ) throws Exception {
        String bucket=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DomainBucketModel domainValueModel) {
            bucket=domainValueModel.getBucket();
        }
        return new ResponseEntity<>(
                this.awsS3Service.thumbnailCreation(file, path,bucket),
                HttpStatus.OK);
    }

    @PostMapping("/videoAndThumbnailUpload")
    public ResponseEntity<VideoAndThumbnailResponse> uploadVideoAndThumbnail(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "videoPath", defaultValue = "MEDIA_VIDEOS") S3Path videoPath,
            @RequestParam(value = "thumbnailPath", defaultValue = "MEDIA_IMAGES") S3Path thumbnailPath,
            HttpServletRequest request
    ) throws Exception {
        String bucket=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DomainBucketModel domainValueModel) {
            bucket=domainValueModel.getBucket();
        }
        ResponseEntity<VideoAndThumbnailResponse> result;
        result = new ResponseEntity(
                this.awsS3Service.videoAndthumbnailUpload(file, videoPath, thumbnailPath,bucket),
                HttpStatus.OK);
        return result;
    }


}