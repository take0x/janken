package oit.is.z1412.kaizi.janken.model;

import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * from users")
  ArrayList<User> selectAllUser();
}
