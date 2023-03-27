package service;

import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.impl.UserServiceImpl;

import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/30
 * @Time: 20:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class UserServiceImplTest {
    @Autowired
    private service.impl.UserServiceImpl userService;
//    @Test
//    public void registerTest(){
//        User user = new User();
//        user.setUsername("sth");
//        user.setPwd("123456");
//        user.setBirthDay("2002-04-06");
//        user.setSex(0);
//        userService.register(user);
//    }
    @Test
    public void login(){
        User user = new User();
        user.setUsername("sth");
        user.setPwd("123456");
        System.out.println(userService.login(user));
    }
    @Test
    public void split(){
        User user = new User();
        user.setUsername("plo");

        List<String> list = userService.findAllFriends(user);
        System.out.println(list.size());
        //ArrayList<String> list = new ArrayList<>(Arrays.asList(str.split(" ")));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void addFriend(){
        User user = new User();
        user.setUsername("plo");
        user.setFriendList("sth");
        userService.addFriend(user);
    }
}
