package plming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {
    @Value("${storage.image.path}")
    public String imagePath;

    @Value("${storage.image.maxSize}")
    public int maxSize;

    public List<String> extensionList = List.of("jpeg","jpg","png","raw","gif","pmp","tiff","psd","tga","svg","heic");

    @Value("${storage.image.requestUrl}")
    public String requestUrl;
}
