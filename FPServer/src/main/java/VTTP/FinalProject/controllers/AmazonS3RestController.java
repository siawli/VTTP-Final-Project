package VTTP.FinalProject.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping("/amazonS3")
public class AmazonS3RestController {

    @Autowired
    private AmazonS3 s3;

    @PostMapping()
    public ResponseEntity<String> uploadFileS3(
            @RequestPart MultipartFile file, String imageUUID) {

        Map<String, String> metadataToAdd = new HashMap<>();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(metadataToAdd);

        try {
            PutObjectRequest putReq = new PutObjectRequest(
                "siawli-vttp", "VTTP-Final-Project/%s".formatted(imageUUID), 
                file.getInputStream(), metadata);
                putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
                s3.putObject(putReq);

                JsonObjectBuilder response = Json.createObjectBuilder();
                return ResponseEntity.ok()
                    .body(response
                        .add("filename", file.getOriginalFilename())
                        .add("filetype", file.getContentType())
                        .build().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("{Unsuccessful upload}");
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getFileS3(@PathVariable String uuid) {

        GetObjectRequest getReq = new GetObjectRequest(
            "siawli-vttp", "VTTP-Final-Project/%s".formatted(uuid));

        S3Object s3Obj = null;
        System.out.println(">>> getting image");
        try {
            System.out.println(">>>> uuid of image: " + uuid);
            s3Obj = s3.getObject(getReq);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        // ObjectMetadata metadata = s3Obj.getObjectMetadata();
        
        byte[] buff = new byte[0];

        try {
            buff = IOUtils.toByteArray(s3Obj.getObjectContent());
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(buff);
    }
}

