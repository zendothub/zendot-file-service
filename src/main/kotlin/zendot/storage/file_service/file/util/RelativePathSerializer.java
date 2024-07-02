package zendot.storage.file_service.file.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import zendot.storage.file_service.file.DomainBucketModel;
import zendot.storage.file_service.file.domain.UploadedFile;

import java.io.IOException;

@JsonComponent
public class RelativePathSerializer extends JsonSerializer<UploadedFile.RelativePath> {

   private String cloudfrontUrl;

    public RelativePathSerializer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DomainBucketModel domainValueModel) {
            cloudfrontUrl=domainValueModel.getCdnUrl();
        }
    }

    @Override
    public void serialize(UploadedFile.RelativePath value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(cloudfrontUrl + value.getPath());
    }
}
