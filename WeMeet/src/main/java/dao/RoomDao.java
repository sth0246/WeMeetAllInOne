package dao;

import entity.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: sth
 * @Description:RoomDao层接口
 * @Date: 2022/11/22
 * @Time: 20:34
 */
@Mapper
public interface RoomDao {
    @Insert("insert into rooms(roomId,status) values(#{roomId},#{status})")
    int insertRoomInit(Room room);
    @Select("select * from rooms where roomId=#{roomId} and status=#{status}")
    List<Room> checkIdStatus(Room room);
    @Select("select * from rooms where status=#{status} limit 100")
    List<Room> selectStatus(Room room);
    @Update("update rooms set userP1=#{userP1},socketIdP1=#{socketIdP1},status=1 where roomId=#{roomId}")
    int updateP1(Room room);
    @Update("update rooms set userP2=#{userP2},socketIdP2=#{socketIdP2},status=2 where roomId=#{roomId}")
    int updateP2(Room room);
    @Update("update rooms set status=#{status} where roomId=#{roomId}")
    int updateStatusByRoomId(Room room);
}
