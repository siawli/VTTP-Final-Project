package VTTP.FinalProject.repositories;

public interface Queries {
    
    public static final String SQL_GET_USER = 
        "select * from users where email = ?";

    public static final String SQL_NEW_USER = 
        "insert into users(username, email, password) values (?, ?, ?)";

    public static final String SQL_NEW_POST = 
        "insert into posts(email, username, title, caption, recipe_id, recipe_label, ratings, likes, date, imageUUID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_GET_ALL_POSTS = 
        "select posts.username, posts.recipe_label, posts.post_id, posts.title, posts.caption, posts.recipe_id, posts.likes, posts.ratings, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts left join (select * from likedPosts where email = ?)  as likedPosts on posts.post_id = likedPosts.post_id where posts.date > NOW() - INTERVAL 60 DAY ORDER BY date ASC;";

    public static final String SQL_ALTER_LIKES_BY_POST =
        "update posts set likes = ? where post_id = ?";
        // update posts set likes = 3 where post_id = 22;
    public static final String SQL_GET_LIKES_BY_POST = 
        "select likes from posts where post_id = ?";

    public static final String SQL_DELETE_LIKED_POST = 
        "delete from likedPosts where post_id = ? and email = ?";
        // delete from likedPosts where post_id = 22 and email = 'tiosiawlilili@gmail.com';
    public static final String SQL_ADD_LIKED_POST = 
        "insert into likedPosts(email, post_id) values (?, ?)";
}
