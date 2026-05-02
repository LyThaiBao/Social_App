package social_app.example.social_app.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import social_app.example.social_app.dto.cloud.UploadResponse;
import social_app.example.social_app.type.MediaType;


import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudServiceImp implements CloudService {
    private final Cloudinary cloudinary;
    @Override
    public UploadResponse upload(MultipartFile file) {
        MediaType type = MediaType.FILE;
        String resourceType = "raw";
        String contentType = file.getContentType();
        assert contentType != null; // constrain deff null
        if(contentType.startsWith("video/")){
            resourceType = "video";
            type = MediaType.VIDEO;
        }
        if(contentType.startsWith("image/")){
            resourceType = "image";
            type = MediaType.IMAGE;
        }
        String nameFile = file.getOriginalFilename();
       long currentTime = System.currentTimeMillis();
        try{
            Map params = ObjectUtils.asMap(
                    "public_id",currentTime+"_"+nameFile,
                            "folder","home/social/messages",
                            "resource_type",resourceType
            );

           Map result =  this.cloudinary.uploader().uploadLarge(file.getInputStream(),params); // send input stream instead byte --> when FE abort it'll break stream and throw ex
            System.out.println("RESULT: "+result);
            return UploadResponse.builder()
                    .mediaUrl(result.get("secure_url").toString())
                    .mediaType(type)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
