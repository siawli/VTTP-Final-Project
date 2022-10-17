package VTTP.FinalProject.repositories;

public interface Queries {
    
    public static final String SQL_GET_USER = 
        "select * from users where email = ?";
    
    public static final String SQL_NEW_USER = 
        "insert into users(username, email, password) values (?, ?, ?)";

    public static final String SQL_NEW_POST = 
        "insert into posts(email, title, caption, recipe_id, ratings, likes, date, imageUUID) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_GET_ALL_POSTS = 
        "select * from posts where date > NOW() - INTERVAL 60 DAY ORDER BY date DESC";
}
