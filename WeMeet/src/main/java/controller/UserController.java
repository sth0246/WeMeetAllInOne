package controller;

import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.impl.RoomServiceImpl;
import service.impl.UserServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/30
 * @Time: 21:03
 */
@Controller //标记为MVC的控制器类，方便DispatcherServlet进行扫描定位
@RequestMapping("/user")
@ResponseBody
@Api(tags = "UserAPI",description = "UserAPI用于用户的各种功能")
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册账号，此处传入完整User类信息")
    boolean register(User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "账户名和用户密码正确则会返回用户所有信息，否则返回空")
    public List<User> login(User user){
        return userService.login(user);
    }
    @PostMapping("/updateUSer")
    @ApiOperation(value = "更新用户数据" ,notes="改接口只能更新username、pwd、birthDay、sex 并且传入uid是必须的。传入时候需传入整个对象")
    public boolean updateUser(User user){
        return userService.updateUser(user);
    }

    @PostMapping("/findAllFriend")
    @ApiOperation(value = "返回该用户所有的好友" ,notes="传入用户名username以获得该用户好友信息，传入时候需传入整个对象")
    public List<String> findAllFriend(User user){
        return userService.findAllFriends(user);
    }
    @PostMapping("/addFriend")
    @ApiOperation(value = "添加好友" ,notes="传入用户名username和要添加的用户名称friendList，传入时候需传入整个对象")
    public boolean addFriend(User user){return userService.addFriend(user);};

}

