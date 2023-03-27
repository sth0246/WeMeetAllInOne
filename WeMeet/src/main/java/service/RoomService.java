package service;

import entity.Room;

import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/22
 * @Time: 20:38
 */
public interface RoomService {
    int insertRoomInit(Room room);
    boolean checkIdStatus(int roomId,int status);
    List<Room> selectStatus(int status);
    boolean updateP1(int roomId,String userP1,String socketIdP1);
    boolean updateP2(int roomId,String userP2,String socketIdP2);
}
