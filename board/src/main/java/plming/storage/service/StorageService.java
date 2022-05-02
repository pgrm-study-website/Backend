package plming.storage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plming.config.StorageProperties;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.user.entity.UserRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {
    private final StorageProperties storageProperties;
    private Path imagePath;

    public StorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        imagePath = Paths.get(storageProperties.imagePath);
    }

    @Transactional
    public String storeImage(MultipartFile file){
        checkFileEmpty(file);
        String extention = getExtention(file.getOriginalFilename());
        checkExtension(extention);
        checkFileSize(file);
        String fileName = makeFileName(extention);
        store(imagePath.resolve(fileName),file);
        return storageProperties.requestUrl + fileName;
    }

    private void store(Path storePath, MultipartFile file){
        try{
            Files.copy(file.getInputStream(),storePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkExtension(String ext){
        ext = ext.toLowerCase();
        if(!storageProperties.extensionList.contains(ext)){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    private void checkFileSize(MultipartFile file){
        if(file.getSize() > storageProperties.maxSize){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    private void checkFileEmpty(MultipartFile file){
        if(file.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    private String getExtention(String fileName){
        return fileName.substring(fileName.lastIndexOf('.')+1);
    }

    private String makeFileName(String extension){
        return UUID.randomUUID().toString() + "." + extension;
    }

    public String urlToFilePath(String url){
        try{
            return storageProperties.imagePath+url.substring(storageProperties.requestUrl.length());
        }catch (IndexOutOfBoundsException e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    public boolean isFileExist(String fileUrl){
        return new File(urlToFilePath(fileUrl)).exists();
    }

    public void deleteImage(String fileUrl){
        String filePath = urlToFilePath(fileUrl);
        File file = new File(filePath);
        if(file.exists()){
            if(!file.delete()){
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }else{
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
    }
}
