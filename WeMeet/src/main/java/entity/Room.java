package entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/22
 * @Time: 19:27
 */
@Data
@ApiModel(value = "Room" ,description = "房间信息实体类")
public class Room {
    @ApiModelProperty(value = "房间号id（唯一、非空）用于p2p视频聊天")
    private Integer roomId;
    @ApiModelProperty(value = "加入房间的用户1的用户名（对应User的userName字段）")
    private String userP1;
    @ApiModelProperty(value = "加入房间的用户1的socket.io分配的id")
    private String socketIdP1;
    @ApiModelProperty(value = "加入房间的用户2的用户名（对应User的userName字段）")
    private String userP2;
    @ApiModelProperty(value = "加入房间的用户2的socket.io分配的id")
    private String socketIdP2;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "房间状态",notes="数据库内预置了1000到9999一万个房间默认status都为0（无需创建房间只需通过status选择可用的房间）------" +
            "0为没有一个人加入此房间（），1为P1加入了房间（此时userP1和socketIdP1应被填入），" +
            "2为P2加入了房间（此时所有字段都应该无空值），，，，，需要注意的是，通过这种方式处理socket房间" +
            "不会出现P2有值而P1无值的情况，因为我们填入顺序永远都是先填P1")
    private Integer status;
}
