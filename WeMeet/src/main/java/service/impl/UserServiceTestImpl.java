package service.impl;

import dao.UserTestDao;
import entity.User;
import entity.UserTest;
import org.springframework.stereotype.Service;
import service.UserServiceTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/10/29
 * @Time: 15:51
 */
@Service("userServiceTest")
public class UserServiceTestImpl implements UserServiceTest {
    @Resource
    private UserTestDao userTestDao;
    @Override
    public List<UserTest> findAllUser() {
        return userTestDao.findAllUser();
    }

}
