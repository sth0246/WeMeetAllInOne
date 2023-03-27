package service.impl;

import dao.UserDao;
import entity.User;
import org.springframework.stereotype.Service;
import service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/30
 * @Time: 20:53
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public boolean register(User user) {
        userDao.register(user);
        return true;
    }

    @Override
    public List<User> login(User user) {
        return userDao.login(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<String> findAllFriends(User user) {
        String allFriends = userDao.findAllFriends(user);
        if(allFriends == null){
            return new ArrayList<String>();
        }else {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(allFriends.split(" ")));

            return list;
        }

    }

    @Override
    public boolean addFriend(User user) {
        String str = userDao.findAllFriends(user);
        if(str == null || str.length()==0){
            str = "";
            str = str.concat(user.getFriendList());
        }else{
            str = str.concat(" ").concat(user.getFriendList());
        }

        user.setFriendList(str);
        userDao.addFriend(user);
        return true;
    }
}
