package zendot.storage.file_service.file.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import zendot.storage.file_service.file.DomainBucketModel;
import zendot.storage.file_service.file.domain.UploadedFile;

import java.io.IOException;

public class RelativePathDeserializer extends JsonDeserializer<UploadedFile.RelativePath> {
    private  String cloudfrontUrl;

    public RelativePathDeserializer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DomainBucketModel domainBucketModel) {
            cloudfrontUrl=domainBucketModel.getCdnUrl();
        }

    }


    @Override
    public UploadedFile.RelativePath deserialize(JsonParser jsonParser,
                                                 DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        return new UploadedFile.RelativePath(jsonParser.getText().substring(cloudfrontUrl.length()));
    }
}
