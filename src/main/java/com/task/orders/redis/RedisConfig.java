//package com.task.orders.redis;
//
//import com.task.orders.config.ConfigParam;
//import com.task.orders.constants.Constants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisAccessor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//public class RedisConfig {
//
//    @Autowired
//    ConfigParam configParam;
//
////    @Bean
////    public JedisConnectionFactory redisConnectionFactory() {
////
////        RedisAccessor redis=new RedisAccessor();
////        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
////        redisConfiguration.setHostName(configParam.getRedisHost());
////        redisConfiguration.setPort(configParam.getRedisPort());
////        redisConfiguration.setUsername(configParam.getRedisUName());
////        redisConfiguration.setPassword(configParam.getRedisPassword());
////        System.out.println("------------------------------  "+configParam.getRedisHost() +" "+configParam.getRedisPort()+
////                " "+configParam.getRedisUName()+" "+configParam.getRedisPassword());
////        return new JedisConnectionFactory(redisConfiguration);
////    }
//@Autowired
//private RedisTemplate<String, String> redisTemplate;
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//
////        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
