package nus.iss.csf.vttpbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.csf.vttpbackend.models.Post;
import nus.iss.csf.vttpbackend.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    public void storePostInCache(String postId, String post) {

        postRepo.storePostInCache(postId, post);
    }

    public String postListing(String postingId) {
       try{
        Post post =  postRepo.getPostFromCache(postingId);
        postRepo.postListing(post);
        return "success";
       } catch (Exception e) {
        return "Not Found";
       }
      
       
    }
    
}
