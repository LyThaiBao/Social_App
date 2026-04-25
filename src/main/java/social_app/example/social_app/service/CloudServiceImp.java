package social_app.example.social_app.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudServiceImp implements CloudService {
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) {
        String resourceType = "raw";
        String contentType = file.getContentType();
        assert contentType != null; // constrain deff null
        if(contentType.startsWith(" video/")){
            resourceType = "video";
        }
        if(contentType.startsWith("image/")){
            resourceType = "image";
        }
        String nameFile = file.getOriginalFilename();
       long currentTime = System.currentTimeMillis();
        try{
            Map params = ObjectUtils.asMap(
                    "public_id",currentTime+"_"+nameFile,
                            "folder","home/social/messages",
                            "resource_type",resourceType
            );
           Map result =  this.cloudinary.uploader().upload(file.getBytes(), params);
            System.out.println("RESULT: "+result);
            return result.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
