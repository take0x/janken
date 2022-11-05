package oit.is.z1412.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Select("select * from matchinfo where isActive=true")
  ArrayList<MatchInfo> selectAllActiveMatch();

  @Select("select * from matchinfo where user2=#{user} and isActive=true")
  ArrayList<MatchInfo> selectAllActiveMatchById(int user);

  @Insert("insert into matchinfo (user1, user2, user1Hand, isActive) values (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("update matchinfo set isActive=false where id=#{id}")
  void updateById(int id);
}
