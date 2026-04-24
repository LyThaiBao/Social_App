package social_app.example.social_app.clodinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.apiName}")
    private String cloudinaryName;

    @Value("${cloudinary.apiKey}")
    private String cloudinaryKey;

    @Value("${cloudinary.apiSecret}")
    private String cloudinarySecret;

    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloudinaryName,
                "api_key",cloudinaryKey,
                "api_secret",cloudinarySecret
        ));
    }
}
