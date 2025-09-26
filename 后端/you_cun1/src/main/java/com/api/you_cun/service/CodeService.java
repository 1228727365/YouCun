package com.api.you_cun.service;

import com.api.you_cun.mapper.CodeMapper;
import com.api.you_cun.pojo.Code;
import com.api.you_cun.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CodeService {
    @Autowired
    CodeMapper codeMapper;

    //查询邮箱所有信息

    public Code getCodeByMail(String  mail){
        return codeMapper.getCodeByMail(mail);
    }

    //更新验证码
    public int updateCode(Code code){
        return codeMapper.updateCode(code);
    }

    //插入验证码
    public int insertCode(Code code) {
        return codeMapper.insertCode(code);
    }

    ////查询验证码时间
    public String getTime(String mail){
        return codeMapper.getTime(mail);
    }


    // 1. 根据邮箱查询最新的验证码记录
    public String getCode(String mail){
        return codeMapper.getCode(mail);
    }

    //更新验证码时间
    public int updateTime(String time,String mail){
        return codeMapper.updateTime(time,mail);
    }
}
