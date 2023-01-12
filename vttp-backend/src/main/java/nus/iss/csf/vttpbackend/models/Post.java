package nus.iss.csf.vttpbackend.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Post {

    private String postingId;
    private String postingDate;
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String image;

    public String getPostingId() {
        return postingId;
    }
    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }
    public String getPostingDate() {
        return postingDate;
    }
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public static Post create(String postingId, String postingDate, String name, String email, String phone, String title, String description, String image) {
        Post post = new Post();

        post.setPostingId(postingId);
        post.setPostingDate(postingDate);
        post.setName(name);
        post.setEmail(email);
        post.setPhone(phone);
        post.setTitle(title);
        post.setDescription(description);
        post.setImage(image);

        return post;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("postingId", postingId)
            .add("postingDate", postingDate)
            .add("name", name)
            .add("email", email)
            .add("phone", phone)
            .add("title", title)
            .add("description", description)
            .add("image", image)
            .build();
    }

    public static Post createFromJson(JsonObject obj) {
        Post post = new Post();

        post.setPostingId(obj.getString("postingId"));
        post.setPostingDate(obj.getString("postingDate"));
        post.setName(obj.getString("name"));
        post.setEmail(obj.getString("email"));
        post.setPhone(obj.getString("phone"));
        post.setTitle(obj.getString("title"));
        post.setDescription(obj.getString("description"));
        post.setImage(obj.getString("image"));

        return post;
    }
    
}
