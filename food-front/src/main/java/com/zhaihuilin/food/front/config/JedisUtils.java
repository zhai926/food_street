package com.zhaihuilin.food.front.config;

import com.zhaihuilin.food.code.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaihuilin on 2018/12/26 17:53.
 */
@Component
public class JedisUtils {

  @Autowired
  RedisTemplate<Object,Object> redisTemplate;

  @Resource(name ="redisTemplate")
  ValueOperations<String,String> valueOperations;

  /**
   * 解决乱码
   * @return
   */
  @Autowired(required = false)
  public void setRedisTemplate(RedisTemplate redisTemplate) {
    RedisSerializer stringSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(stringSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(stringSerializer);
    this.redisTemplate = redisTemplate;
  }


  public String get(String key){
    String value = valueOperations.get(key);
    if(StringUtils.isNotEmpty(value)){
      return value;
    }
    return null;
  }

  public void set (String key,String value){
    valueOperations.set(key, value);
  }

  /**
   TimeUnit.DAYS          //天
   TimeUnit.HOURS         //小时
   TimeUnit.MINUTES       //分钟
   TimeUnit.SECONDS       //秒
   TimeUnit.MILLISECONDS  //毫秒
   TimeUnit.NANOSECONDS   //毫微秒
   TimeUnit.MICROSECONDS  //微秒
   * @param key
   * @param value
   * @param outTime
   */
  public void set(String key,String value,long outTime){
    valueOperations.set(key, value, outTime, TimeUnit.MINUTES);
  }
}
