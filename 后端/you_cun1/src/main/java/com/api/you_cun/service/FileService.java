package com.api.you_cun.service;

import com.api.you_cun.mapper.FileMapper;
import com.api.you_cun.pojo.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    FileMapper  fileMapper;

    //创建文档
    public int insertFile(int uid,String content,String date,String token,int state,String password,String AesKey){
        return fileMapper.insertFile(uid,content,date,token,state,password,AesKey);
    }

    //设置文档tokne
    public int setFileToken(Integer fid,String filetoken){
        return fileMapper.setFileToken(fid,filetoken);
    }

    //设置文档password
    public int setFilePassword(Integer fid,String filepassword){
        return fileMapper.setFilePassword(fid,filepassword);
    }

    //设置文档AesKey
    public int setFileAesKey(Integer fid,String AesKey){
        return fileMapper.setFileAesKey(fid,AesKey);
    }

    //设置文档状态
    public int setFileState(Integer fid,Integer state){
        return fileMapper.setFileState(fid,state);
    }

    //查询文档
    public File getFile(String Ftoken){
        return fileMapper.getFile(Ftoken);
    }

    //查询所有文档
    public File[] getFilesByPage(String uid, Integer pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        int page = (pageNum - 1) * 8;
        return fileMapper.getFilesByPage(uid, page);
    }

    //删除文档
    public int deleteFile(int id,int uid){
        return fileMapper.deleteFile(id,uid);
    }

    //修改文档
    public int updateFile(int id,String content,int uid){
        return fileMapper.updateFile(id,content,uid);
    }

    //更新文档时间
    public int updateFileDate(Integer id){
        return fileMapper.updateFileDate(id);
    }

    //获取文档数量的总数
    public int getFileCountAll(){
        return fileMapper.getFileCountAll();
    }

}
