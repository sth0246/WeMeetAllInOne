package dao;

import dao.UserTestDao;
import entity.UserTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: sth
 * @Description: 用于测试Dao层作用
 * @Date: 2022/10/30
 * @Time: 15:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class UserDaoTest {
    @Autowired
    private UserTestDao userTestDao;

    @Test
    public void myTest(){
        for (UserTest userTest : userTestDao.findAllUser()) {
            System.out.println(userTest);
        }
    }


}
