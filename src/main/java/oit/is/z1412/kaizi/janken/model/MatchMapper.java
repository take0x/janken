package oit.is.z1412.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("select * from matches")
  ArrayList<Match> selectAllMatch();

  @Insert("insert into matches (user1, user2, user1Hand, user2Hand, isActive) values (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("update matches set isActive=false where id=#{id}")
  void updateById(int id);
}
