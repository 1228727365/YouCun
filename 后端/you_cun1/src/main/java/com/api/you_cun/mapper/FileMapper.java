package com.api.you_cun.mapper;

import com.api.you_cun.pojo.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    //创建文档
    @Insert("insert into file(uid,content,date,token,state,password,AesKey) values(#{uid},#{content},#{date},#{token},#{state},#{password},#{AesKey}) ")
    int insertFile(int uid,String content,String date,String token,int state,String password,String AesKey);
    //更新文档token
    @Update("update file set token = #{Ftoken} where id = #{id}")
    int setFileToken(Integer id,String Ftoken);
    //更新文档password
    @Update("update file set password = #{password} where id = #{id}")
    int setFilePassword(Integer id,String password);
    //更新文档AES加密的密钥
    @Update("update file set AesKey = #{AesKey} where id = #{id}")
    int setFileAesKey(Integer id,String AesKey);
    //更新文档状态
    @Update("update file set state = #{state} where id = #{id}")
    int setFileState(Integer id,Integer state);
    //查询文档
    @Select("select * from file where token = #{token}")
    File getFile(String token);
    //查询所有文档
    @Select("select * from file where uid = #{uid} order by id desc limit 8 offset #{page}")
    File[] getFilesByPage(String uid, @Param("page") int page);
    //删除文档
    @Delete("delete from file where id = #{id} and uid=#{uid}")
    int deleteFile(int id,int uid);
    //修改文档
    @Update("update file set content = #{content} where id = #{id} and uid=#{uid}")
    int updateFile(int id,String content,int uid);
    //更新文档当前时间系统时间
    @Update("update file set date = NOW() where id = #{id}")
    int updateFileDate(Integer id);


    //获取文档数量的总数
    @Select("select count(*) from file")
    int getFileCountAll();
}
