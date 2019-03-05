package com.zhaihuilin.food.front.config;


import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.greens.GreensService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具 可以进行模糊查询
 * Created by zhaihuilin on 2018/12/26 17:53.
 */
@Component
@Log4j
public class MyJedisUtils  implements InitializingBean {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Autowired
  private GreensService greensService;

  ValueOperations<String,String> valueOperations;

  @Override
  public void afterPropertiesSet() throws Exception {
    RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
    redisTemplate.setKeySerializer(redisSerializer);
    redisTemplate.setHashKeySerializer(redisSerializer);
    valueOperations = redisTemplate.opsForValue();
  }

  /**
   * 解决乱码
   * @param factory
   * @return
   */
  @Autowired(required = false)
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
    redisTemplate.setConnectionFactory(factory);
    //key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
    //所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
    //或者JdkSerializationRedisSerializer序列化方式;
    RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
    redisTemplate.setKeySerializer(redisSerializer);
    redisTemplate.setHashKeySerializer(redisSerializer);

    return redisTemplate;
  }


  /**
   * 缓存
   * @param key
   * @param value
   */
  public void  set(String key, String value){
    valueOperations.set(key, value);
  }

  /**
   * 设置  带 过期时间
   * @param key
   * @param value
   * @param outTime
   */
  public void  set(String key, String value,long outTime){
    try {
      valueOperations.set(key, value,outTime, TimeUnit.MINUTES);
    }catch (Exception e){
       log.info("获取的原因是:"+ e.getMessage());
    }
  }

  /**
   * 获取值
   * @param key
   * @return
   */
  public List<Greens> get(String key){
    Set<String> keys =redisTemplate.keys(key+ "*");
    log.info("获取的keys：" + keys.toString());
    if (CollectionUtils.isEmpty(keys)) {
      return new ArrayList<>();
    }
    List<Greens> greensList = new ArrayList<>();
    for (String k: keys ){
       String  value =valueOperations.get(k);
       if (value!=null){
         Greens greens = greensService.findByGreensIdAndDelFalse(value);
         if (greens!=null){
            greensList.add(greens);
         }
       }
    }
    if (greensList!=null && greensList.size()>0){
        return  greensList;
    }else{
        return  null;
    }
  }
}
