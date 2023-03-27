package service;

import dao.UserDao;
import entity.Room;
import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.impl.RoomServiceImpl;
import service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/12/11
 * @Time: 16:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class RoomServiceImplTest {
    @Autowired
    RoomServiceImpl roomService;
    @Test
    public void init(){
        Room room = new Room();
        for (int i = 1000; i <= 9999 ; i++) {
            room.setRoomId(i);
            room.setStatus(0);
            roomService.insertRoomInit(room);
        }


    }
}
