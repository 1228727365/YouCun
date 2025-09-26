package com.api.you_cun.mapper;

import com.api.you_cun.pojo.Code;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CodeMapper {
    //查询邮箱所有信息
    @Select("SELECT * FROM code WHERE mail = #{mail}")
    Code getCodeByMail(String mail);

    //更新验证码
    @Update("update code set code = #{code}, time = #{time} where mail = #{mail}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int updateCode(Code code);

    //插入验证码
    @Insert("insert into code(mail, code, time) values(#{mail}, #{code}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCode(Code code);

    //查询验证码
    @Select("select code from code where mail = #{mail} order by time desc limit 1")
    String getCode(String mail);

    //查询验证码时间
    @Select("select time from code where mail = #{mail} order by time desc limit 1")
    String getTime(String mail);


    //更新验证码时间
    @Update("update code set time = #{time} where mail = #{mail}")
    int updateTime(String time,String mail);

}
