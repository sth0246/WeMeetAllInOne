package service.impl;

import dao.RoomDao;
import entity.Room;
import org.springframework.stereotype.Service;
import service.RoomService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sth
 * @Description:
 * @Date: 2022/11/22
 * @Time: 20:38
 */
@Service("RoomService")
public class RoomServiceImpl implements RoomService {
    @Resource
    RoomDao roomDao;
    @Override
    public int insertRoomInit(Room room) {
        return roomDao.insertRoomInit(room);
    }

    @Override
    public boolean checkIdStatus(int roomId, int status) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setStatus(status);
        if (roomDao.checkIdStatus(room).size()==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public List<Room> selectStatus(int status) {
        Room room = new Room();
        room.setStatus(status);

        return roomDao.selectStatus(room);
    }

    @Override
    public boolean updateP1(int roomId, String userP1, String socketIdP1) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setUserP1(userP1);
        room.setSocketIdP1(socketIdP1);
        roomDao.updateP1(room);
        return true;
    }
    public boolean updateP2(int roomId, String userP2, String socketIdP2) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setUserP2(userP2);
        room.setSocketIdP2(socketIdP2);
        room.setStatus(2);
        roomDao.updateP2(room);
        return true;
    }
    public boolean updateStatusByRoomId(int roomId,int status){
        Room room = new Room();
        room.setRoomId(roomId);
        room.setStatus(status);
        roomDao.updateStatusByRoomId(room);
        return true;
    }
}
