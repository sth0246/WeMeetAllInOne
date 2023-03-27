package service;

import entity.User;

import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/30
 * @Time: 20:48
 */
public interface UserService {
    boolean register(User user);
    List<User> login(User user);
    boolean updateUser(User user);
    List<String> findAllFriends(User user);
    boolean addFriend(User user);
}
