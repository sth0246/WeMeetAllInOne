-- 创建数据库
CREATE DATABASE IF NOT EXISTS `webrtc`;
-- 使用数据库
USE `webrtc`;

-- 简单的创建表结构，用于测试
CREATE TABLE IF NOT EXISTS `usertest`(
                                      `name` VARCHAR(50),
                                      `pwd`  VARCHAR(50)
);
-- 创建 用户表
CREATE TABLE IF NOT EXISTS `users` (
                           `uid`             BIGINT          UNSIGNED    NOT NULL 	auto_increment   COMMENT '主键ID',
                           `username`        VARCHAR(32)     UNIQUE                                  COMMENT '用户名称',
                           `pwd`             VARCHAR(64)                                             COMMENT '密码',
                           `hobbiesList`     VARCHAR(512)                                            COMMENT '兴趣以空格隔开后端可用split得到列表',
                           `birthDay`        DATE            NOT NULL                                COMMENT '出生日期，前端可以获取年龄',
                           `sex`             TINYINT         NOT NULL                                COMMENT '性别女为0，男为1',
                           `friendList`      VARCHAR(512)                                            COMMENT '好友列表以空格隔开',

                           PRIMARY KEY (`uid`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci' COMMENT='用户表';
-- 查询 测试表
SELECT * FROM users;

CREATE TABLE IF NOT EXISTS `rooms`(
    `roomId` INT UNIQUE PRIMARY KEY NOT NULL ,
    `userP1` varchar(32),
    `socketIdP1` varchar(64),
    `userP2` varchar(32),
    `socketIdP2` varchar(64),
    `createTime` DATETIME,
    `status` tinyint not null default 0
)ENGINE=INNODB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci' COMMENT='正在进行的房间表';
