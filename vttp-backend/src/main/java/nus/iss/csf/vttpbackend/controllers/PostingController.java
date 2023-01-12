package nus.iss.csf.vttpbackend.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nus.iss.csf.vttpbackend.models.Post;
import nus.iss.csf.vttpbackend.models.Response;
import nus.iss.csf.vttpbackend.services.PostService;

@RestController
@RequestMapping("/api")
public class PostingController {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private PostService postSvc;

    private String IMG_URL = "https://lyonvttp.sgp1.digitaloceanspaces.com/vttp-assessment/";
    
    @PostMapping(path = "/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPost(@RequestPart MultipartFile file, @RequestPart String name, @RequestPart String email, @RequestPart String phone, @RequestPart String title, @RequestPart String description) {

        Map<String,String> myData = new HashMap<>();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(myData);

        String postId = UUID.randomUUID().toString().substring(0, 8);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();
        
        String imageUrl = IMG_URL + postId;

        Post post = Post.create(postId, formatter.format(date), name, email,phone,title,description,imageUrl);

        postSvc.storePostInCache(postId, post.toJson().toString());
        System.out.println(postId);

        try {
            PutObjectRequest putReq = new PutObjectRequest("lyonvttp", "vttp-assessment/%s".formatted(postId), 
            file.getInputStream(), metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putReq);

            return ResponseEntity.status(HttpStatus.OK).body(post.toJson().toString());

        } catch (Exception ex) {
            Response resp = new Response();
            ex.printStackTrace();
            resp.setCode(400);
            resp.setMessage("400 Bad Request");
            // resp.setData("Image upload has failed");
            return ResponseEntity.status(HttpStatus.OK).body(resp.toString());
        }

    }

    @PutMapping("/posting/{postingId}")
    public ResponseEntity<String> confirmPost(@PathVariable String postingId) {

        if (postSvc.postListing(postingId) == "success") {
            Response resp = new Response();

            resp.setMessage("Accepted %s".formatted(postingId));
            resp.setCode(201);

            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toJson().toString());
        } else {
            Response resp = new Response();
            resp.setMessage("Posting ID %s not found".formatted(postingId));
            resp.setCode(400);

            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toJson().toString());
        }
        

        
    }
}
