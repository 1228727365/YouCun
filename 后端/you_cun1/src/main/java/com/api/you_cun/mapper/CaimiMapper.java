package com.api.you_cun.mapper;

import com.api.you_cun.pojo.Caimi;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
public interface CaimiMapper {

    // 批量添加卡密
    @Insert("<script>" +
            "INSERT INTO km(km, `value`, `type`) VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.km}, #{item.value}, #{item.type})" +
            "</foreach>" +
            "</script>")
    int addCaimi(List<Map<String, String>> caimiList);


    //获取所有卡密
    @Select("select * from km")
    List<Caimi> getCaimiAll();


    //通过卡密查询卡密信息
    @Select("select * from km where km = #{km}")
    Caimi getCaimi(String km);

    //通过id删除卡密
    @Insert("delete from km where id = #{id}")
    int deleteCaimi(int id);
}
