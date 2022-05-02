package plming.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plming.auth.service.JwtTokenProvider;
import plming.config.StorageProperties;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.storage.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/storage")
@Validated
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final StorageProperties storageProperties;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public String storeProfileImage(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) {
        jwtTokenProvider.validateToken(request);
        if(file == null){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return storageService.storeImage(file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try{
            File file = new File(storageProperties.imagePath+fileName);
            ResponseEntity<byte[]> response;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", Files.probeContentType(file.toPath()));
            response = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
