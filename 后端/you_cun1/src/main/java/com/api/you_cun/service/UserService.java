package com.api.you_cun.service;

import com.api.you_cun.mapper.CaimiMapper;
import com.api.you_cun.mapper.CodeMapper;
import com.api.you_cun.mapper.UserMapper;
import com.api.you_cun.pojo.Caimi;
import com.api.you_cun.pojo.Code;
import com.api.you_cun.pojo.User;
import com.api.you_cun.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    CaimiMapper caimiMapper;

    //查询用户所有信息
    public User getUser(String mail){
        return userMapper.getUser(mail);
    }



    public int insertUser(String mail, String password) {
        return userMapper.insertUser(mail, Tool.md5Encrypt(password),Tool.Token(),0,"0",0,0,"0");
    }

    //用户信息认证
    public User getUserByToken(String token,String mail){
        return userMapper.getUserByToken(token,mail);
    }

    //登陆
    public User Login(String mail, String User_password) {
        User user = userMapper.getUser(mail);
        return user;
    }

    //增加文档数量
    public int addFileNumber(int id){
        return userMapper.addFileNumber(id);
    }

    //删除文档数量
    public int subFileNumber(int id){
        return userMapper.subFileNumber(id);
    }


    //增加图片数量
    public int addImgNumber(int id){
        return userMapper.addImgNumber(id);
    }

    //减少图片数量
    public int subImgNumber(int id){
        return userMapper.subImgNumber(id);
    }



    //增加存储数量
    public int updateUserSize(int id,Long userSize){
        return userMapper.updateUserSize(id,userSize);
    }

    //减少存储数量
    public int subUserSize(int id,Long useSize){
        return userMapper.subUserSize(id,useSize);
    }


    //更新用户token
    public int updataToken(String Token,Integer id){
        return userMapper.updateToken(Token,id);
    }

    //修改密码
    public int updataPassword(String password,Integer id){
        return userMapper.updataPassword(password,id);
    }

    //使用卡密
    public Caimi getCaimi(String km){
        return caimiMapper.getCaimi(km);
    }

    //会员增加
    public int updataVip(Long vip_time,Integer id){
        return userMapper.updataVip(vip_time,id);
    }

    //扩展用户内存
    public int updateMoreSize(int id,Long moreSize){
        return userMapper.updateMoreSize(id,moreSize);
    }

    //获取用户数量的总数
    public int getUserCountAll(){
        return userMapper.getUserCountAll();
    }


}
