package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.Recipe;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@Service
public class CachingService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // @RedisHash(timeToLive = 60*15)
    @Cacheable(value="query", key = "#query.toString() + #pageNum.toString()")
    public List<Recipe> getListOfRecipesId(JsonObject data, String query, int pageNum) {
        List<Recipe> recipes = new LinkedList<>();

        JsonArray hits = data.getJsonArray("hits");
        for (JsonValue jsonObj : hits) {
            JsonObject recipe = jsonObj.asJsonObject().getJsonObject("recipe");

            recipes.add(EdamamService.getSimilarData(recipe));
        }
        System.out.println(">>>> key: " + "query::%s%d".formatted(query, pageNum));
        System.out.println(">>> value?: " + redisTemplate.keys("query"));
        redisTemplate.expire("query::%s%d".formatted(query, pageNum), 60*15, TimeUnit.SECONDS);

        return recipes;
    }

    
}
