package nus.iss.csf.vttpbackend.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.iss.csf.vttpbackend.models.Post;

@Repository
public class PostRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JdbcTemplate template;

    private final String SQL_POST_LISTING = "insert into postings(posting_id, posting_date, name, email, phone, title, description, image) values(?,?,?,?,?,?,?,?)";
    
    public void storePostInCache(String postId, String post) {

        redisTemplate.opsForValue().set(postId, post);
        
        Duration timeout = Duration.parse("PT10M");
        redisTemplate.expire(postId, timeout);

    }

    public Post getPostFromCache(String postingId) {
        String postString = (String) redisTemplate.opsForValue().getAndDelete(postingId);

        try (InputStream is = new ByteArrayInputStream(postString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject obj = reader.readObject();
            Post post = Post.createFromJson(obj);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean postListing(Post post) {

        int updated = template.update(SQL_POST_LISTING, post.getPostingId(), post.getPostingDate(), post.getName(), post.getEmail(), post.getPhone(), post.getTitle(), post.getDescription(), post.getImage());

        return updated == 1;
    }
}
