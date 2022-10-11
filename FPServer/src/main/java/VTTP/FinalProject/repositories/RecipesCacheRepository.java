// package VTTP.FinalProject.repositories;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Repository;

// import VTTP.FinalProject.models.RecipeResponse;

// @Repository
// public class RecipesCacheRepository {

//     @Autowired
//     @Qualifier("recipes")
//     private RedisTemplate<String, Object> redisTemplate;
    
//     public void storeQueryByPage(String query, int pageNum, RecipeResponse recipeResp) {
//         redisTemplate.opsForHash().put(query, pageNum, recipeResp);
//     }

//     public void deleteQueryByPage(String query, int pageNum) {
//         redisTemplate.opsForHash().delete(query, pageNum);
//     }
    
// }
