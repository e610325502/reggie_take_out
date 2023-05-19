package com.itheima.reggie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static org.apache.coyote.http11.Constants.a;

//@SpringBootTest
public class test1 {

    @Test
    public void test(){
        PriorityQueue<Integer> queue=new PriorityQueue<>((a,b)->a-b);
        queue.offer(1);
        queue.offer(6);
        queue.offer(3);
        queue.offer(5);
        queue.offer(2);
        System.out.println(queue);


    }

}
