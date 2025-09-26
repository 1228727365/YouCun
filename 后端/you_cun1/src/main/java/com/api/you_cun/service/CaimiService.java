package com.api.you_cun.service;

import com.api.you_cun.mapper.CaimiMapper;
import com.api.you_cun.pojo.Caimi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CaimiService {

    @Autowired
    CaimiMapper caimiMapper;


    //添加卡密
    public int addCaimi(List<Map<String, String>> caimiList){
        return caimiMapper.addCaimi(caimiList);
    }

    //查询所有卡密
    public List<Caimi> getCaimiAll(){
        return caimiMapper.getCaimiAll();
    }

    //通过id删除卡密
    public int deleteCaimi(int id){
        return caimiMapper.deleteCaimi(id);
    }

}
