// package VTTP.FinalProject.configurations;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Scope;
// import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
// import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
// import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.StringRedisSerializer;

// @Configuration
// public class RedisConfiguration {

//     @Value("${spring.redis.host}")
//     private String redisHost;

//     @Value("${spring.redis.port}")
//     private Integer redisPort;

//     @Value("${spring.redis.password}")
//     private String redisPassword;

//     @Bean(name="recipes")
//     public RedisTemplate<String, Object> createRedisTemplate() {

//         final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//         config.setPort(redisPort);
//         config.setHostName(redisHost);
//         config.setPassword(redisPassword);

//         final JedisClientConfiguration jedisClient = 
//                 JedisClientConfiguration.builder().build();
//         final JedisConnectionFactory jedisFac = 
//                 new JedisConnectionFactory(config, jedisClient);
//         jedisFac.afterPropertiesSet();

//         final RedisTemplate<String, Object> template = new RedisTemplate<>();
//         template.setConnectionFactory(jedisFac);
// 		template.setKeySerializer(new StringRedisSerializer());
// 		template.setValueSerializer(new StringRedisSerializer());
// 		template.setHashKeySerializer(new StringRedisSerializer());
// 		template.setHashValueSerializer(new StringRedisSerializer());

//         return template;
//     }
    
// }
