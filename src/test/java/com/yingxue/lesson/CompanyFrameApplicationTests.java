package com.yingxue.lesson;


import com.yingxue.lesson.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yingxue.lesson.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyFrameApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private RedisService redisService;

    @Test
    public void testRedis(){
        redisService.set("redisTest","redis test");
        System.out.println(redisService.get("redisTest"));
    }



}
