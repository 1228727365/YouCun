package com.api.you_cun.mapper;


import com.api.you_cun.pojo.Image;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ImageMapper {

    //上传图片
    @Insert("insert into image(uid,name,size,date) values(#{uid},#{name},#{size},#{date})")
    int insertImage(int uid,String name,String size,String date);
    //获取图片
    @Select("select name from image where id = #{id}")
    String getImage(Integer id);
    //删除图片
    @Delete("delete from image where id = #{id} and uid = #{uid} ")
    int deleteImage(int id,int uid);
    //获取图片size
    @Select("select size from image where id = #{id}")
    String getImageSize(Integer id);
    //获取所有图片
    @Select("select * from image where uid = #{uid} order by id desc limit 8 offset #{page}")
    Image[] getImageByPage(String uid, @Param("page") int page);
    //查询图片名称
    @Select("select name from image where id = #{id}")
    String getImageName(Integer id);

    //获取图片数量的总数
    @Select("select count(*) from image")
    int getImageCountAll();

}
