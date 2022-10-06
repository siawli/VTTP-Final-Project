package VTTP.FinalProject.repositories;

public interface Queries {
    
    public static final String SQL_GET_USER = 
        "select * from users where email = ?";
    
    public static final String SQL_NEW_USER = 
        "insert into users(username, email, password) values (?, ?, ?)";
}
