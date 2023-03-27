package dao;

import entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: sth
 * @Description:UserDao层接口
 * @Date: 2022/11/22
 * @Time: 20:33
 */
@Mapper
public interface UserDao {
    @Insert("insert into users(`username`,`pwd`,`birthDay`,`sex`) values(#{username},#{pwd},#{birthDay},#{sex})")
    boolean register(User user);
    @Select("select * from users where username=#{username} and pwd=#{pwd}")
    List<User> login(User user);
    @Update("update users set username=#{username},pwd=#{pwd},birthDay=#{birthDay},sex=#{sex} where uid = #{uid}")
    boolean updateUser(User user);
    @Select("select friendList from users where username=#{username}")
    String findAllFriends(User user);
    @Update("update users set friendList=#{friendList} where username=#{username}")
    boolean addFriend(User user);
}
