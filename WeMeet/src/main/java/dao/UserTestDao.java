package dao;

import entity.UserTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: sth
 * @Description: UserTest Dao层接口，用于将xml的sql代码交给spring处理
 * @Date: 2022/10/29
 * @Time: 13:07
 */
@Mapper
public interface UserTestDao {
    List<UserTest> findAllUser();



}