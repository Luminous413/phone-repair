package com.lmscr.testspring;

import com.lmscr.testspring.pojo.User;
import com.lmscr.testspring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneRepairApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello");
    }

    /**
     * 做测试
     */
    @Autowired
    private UserService userService;
    @Test
    void testLink(){
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(user);
        }
    }

}
