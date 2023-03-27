package controller;

import entity.User;
import entity.UserTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserServiceTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sth
 * @Description: 用于UserTestController测试
 * @Date: 2022/10/29
 * @Time: 15:53
 */
@Controller //标记为MVC的控制器类，方便DispatcherServlet进行扫描定位
@RequestMapping("/test")
@Api(tags = "UserTestAPI",description = "UserTestAPI测试")
public class UserTestController {
    @Resource
    private UserServiceTest userServiceTest;

    @PostMapping("/findAll")
    @ResponseBody
    @ApiOperation(value = "测试value",notes = "测试notes")
    public List<UserTest> findAll(){
        return userServiceTest.findAllUser();
    }


//多个servlet
    @PostMapping("/findAll1")
    @ResponseBody
    @ApiOperation(value = "测试value22222")
    public List<UserTest> findAll1(){
        return userServiceTest.findAllUser();
    }


    @PostMapping("/findAll2")
    @ResponseBody
    public List<UserTest> findAll2(){
        return userServiceTest.findAllUser();
    }


}
