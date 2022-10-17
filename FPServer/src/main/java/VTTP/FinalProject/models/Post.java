package VTTP.FinalProject.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Post {
    private int post_id;
    private String email;
    private String title;
    private String caption;
    private String recipe_id;
    private Float ratings;
    private int likes;
    private String date;
    private String imageUUID;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getRecipe_id() {
        return recipe_id;
    }
    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }
    public Float getRatings() {
        return ratings;
    }
    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getPost_id() {
        return post_id;
    }
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    public String getImageUUID() {
        return imageUUID;
    }
    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }
    @Override
    public String toString() {
        return "Post [post_id=" + post_id + ", title=" + title + ", caption=" + caption + ", recipe_id=" + recipe_id
                + ", ratings=" + ratings + ", likes=" + likes + ", date=" + date + ", imageUUID=" + imageUUID + "]";
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static Post createPost(SqlRowSet result) {
        Post post = new Post();
        post.setTitle(result.getString("title"));
        post.setCaption(result.getString("caption"));
        post.setDate(result.getString("date"));
        // post.setEmail(result.getString("email"));
        post.setImageUUID(result.getString("imageUUID"));
        post.setLikes(result.getInt("likes"));
        post.setPost_id(result.getInt("post_id"));
        post.setRatings(result.getFloat("ratings"));
        post.setRecipe_id(result.getString("recipe_id"));

        // System.out.println("in model post: " + post.toString());

        return post;
    }

}
