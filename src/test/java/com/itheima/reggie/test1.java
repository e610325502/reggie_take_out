package com.itheima.reggie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class test1 {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("city1","haikoi");
        String city = (String) valueOperations.get("city");
        String city1 = (String) valueOperations.get("city1");
        System.out.println(city);
        System.out.println(city1);
    }
}
