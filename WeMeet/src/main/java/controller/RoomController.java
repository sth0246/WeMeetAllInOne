package controller;

import entity.Room;
import entity.User;
import entity.UserTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserServiceTest;
import service.impl.RoomServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sth
 * @Description: roomController接口
 * @Date: 2022/11/22
 * @Time: 21:07
 */
@Controller //标记为MVC的控制器类，方便DispatcherServlet进行扫描定位
@RequestMapping("/room")
@ResponseBody
@Api(tags = "RoomAPI",description = "RoomAPI用于房间的查询填入管理等")
public class RoomController {
    @Resource
    private RoomServiceImpl roomServiceImpl;

    @PostMapping("/test")
    @ApiOperation(value = "测试")
    public int test(){
        Room room = new Room();
        User user = new User();
        return 1;

    }

    @PostMapping("/checkIdStatus")
    @ResponseBody
    @ApiOperation(value = "用于确定一个房间号是什么状态的",notes = "roomId是房间号，status是状态，返回bool值")
    public boolean checkIdStatus(int roomId,int status){
        return roomServiceImpl.checkIdStatus(roomId,status);
    }

    @PostMapping("/selectStatus")
    @ResponseBody
    @ApiOperation(value = "用于通过状态查找房间号",notes = "参数为status状态信息")
    public List<Room> selectByStatus(int status){
        if (status==0){
            return roomServiceImpl.selectStatus(status).subList(0,3);
        }
        return roomServiceImpl.selectStatus(status);

    }


    @PostMapping("/updateP1")
    @ApiOperation(value = "P1加入房间时依据房间号更新房间的userP1、socketIdP1")
    public boolean updateP1(int roomId,String userP1,String socketIdP1){
        return roomServiceImpl.updateP1(roomId,userP1,socketIdP1);
    }
    @PostMapping("/updateP2")
    @ApiOperation(value = "P1加入房间时依据房间号更新房间的userP2、socketIdP2")
    public boolean updateP2(int roomId,String userP2,String socketIdP2){
        return roomServiceImpl.updateP2(roomId,userP2,socketIdP2);
    }
    @PostMapping("/initRoom")
    @ApiOperation(value = "通话结束时销毁房间传入房间id")
    public boolean initRoom(int roomId){
        return roomServiceImpl.updateStatusByRoomId(roomId,0);
    }
}
