package com.api.you_cun.mapper;

import com.api.you_cun.pojo.Code;
import com.api.you_cun.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    //通过tokne和mail查询所有信息
    @Select("select * from user where token = #{token} and mail = #{mail}")
    User getUserByToken(String token,String mail);
    //插入用户
    @Insert("insert into user(mail,password,token,moreSize,useSize,fileNumber,imgNumber,vip_time) values(#{mail},#{password},#{token},#{moreSize},#{useSize},#{fileNumber},#{imgNumber},#{vip_time})")
    int insertUser(String mail,String password,String token,int moreSize,String useSize,int fileNumber,int imgNumber,String vip_time);
    //查询用户所有信息
    @Select("select * from user where mail = #{mail}")
    User getUser(String mail);
    //将文档数量加1
    @Insert("update user set fileNumber = fileNumber + 1 where id = #{id}")
    int addFileNumber(int id);
    //将文档数量减1
    @Insert("update user set fileNumber = fileNumber - 1 where id = #{id}")
    int subFileNumber(int id);
    //增加存储内存
    @Update("update user set useSize = useSize + #{useSize} where id = #{id}")
    int updateUserSize(int id,Long useSize);
    //减少存储内存
    @Update("update user set useSize = useSize - #{useSize} where id = #{id}")
    int subUserSize(int id,Long useSize);
    //增加图片
    @Insert("update user set imgNumber = imgNumber + 1 where id = #{id}")
    int addImgNumber(int id);
    //减少图片
    @Insert("update user set imgNumber = imgNumber - 1 where id = #{id}")
    int subImgNumber(int id);

    //更新token
    @Update("update user set token = #{token} where id = #{id}")
    int updateToken(String token,Integer id);

    //修改密码
    @Update("update user set password=#{password} where id=#{id}")
    int updataPassword(String password,Integer id);

    //会员增加
    @Update("update user set vip_time = #{vip_time} where id = #{id}")
    int updataVip(Long vip_time,Integer id);

    //扩展用户内存
    @Update("update user set moreSize = moreSize + #{moreSize} where id = #{id}")
    int updateMoreSize(int id,Long moreSize);

    //获取用户数量的总数
    @Select("select count(*) from user")
    int getUserCountAll();
}
