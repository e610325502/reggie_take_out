package com.itheima.reggie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class test1 {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
        public void test(){
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(4);
        for(int i=0;i<list.size();i++){
            if(list.get(i)==2){
                list.remove(i);
                i--;
            }
        }
        System.out.println(list);
    }
}
