package entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Author: sth
 * @Description: 用户实体类
 * @Date: 2022/11/22
 * @Time: 15:47
 */
@Data
@ApiModel(value = "User",description = "User实体类")
public class User {
    @ApiModelProperty(value = "用户名称（昵称）登录时候可以使用该属性登录")
    private String username;
    @ApiModelProperty(value = "用户uid用于后台唯一标记用户",notes = "uid为数据库自增字段，创建用户时不必加入uid")
    private Integer uid;
    @ApiModelProperty(value = "用户密码")
    private String pwd;
    @ApiModelProperty(value = "用户生日，前端可以得到用户年龄，方便匹配")
    private String birthDay;
    @ApiModelProperty(value = "用户性别0为女，，，，1为男")
    private Integer sex;
    @ApiModelProperty(value = "用户的好友列表，在后端中使用整个String存储用空格隔开",allowEmptyValue = true)
    private String friendList;
    @ApiModelProperty(value = "用户的兴趣列表，在后端中使用整个String存储用空格隔开",allowEmptyValue = true)
    private String hobbiesList;
}
