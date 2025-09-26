package com.api.you_cun.service;

import com.api.you_cun.config.config;
import com.api.you_cun.mapper.ImageMapper;
import com.api.you_cun.pojo.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    ImageMapper  imageMapper;

    //上传图片
    public int insertImage(int uid,String name,String size,String date){
        return  imageMapper.insertImage(uid,name,size,date);
    }


    //获取图片
    public String getImage(Integer id){
        return imageMapper.getImage(id);
    }

    //删除图片
    public int DelImage(Integer id,Integer uid){
        return imageMapper.deleteImage(id,uid);
    }


    //获取图片size
    public String getImageSize(Integer id){
        return imageMapper.getImageSize(id);
    }

    //获取图片name
    public String getImageName(Integer id){
        return imageMapper.getImageName(id);
    }

    //查询所有图片
    public Image[] getImageByPage(String uid, Integer pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        }

        int page = (pageNum - 1) * 8;
        Image[] imageDOs = imageMapper.getImageByPage(uid,page);

        Image[] images = new Image[imageDOs.length];
        String baseUrl = config.wz;

        for (int i = 0; i < imageDOs.length; i++) {
            Image image = imageDOs[i];
            images[i] = new Image(
                    image.getId(),
                    image.getUid(),
                    image.getName(),
                    image.getSize(),
                    image.getDate(),
                    baseUrl
            );
        }

        return images;
    }

    //获取图片数量的总数
    public int getImageCountAll(){
        return imageMapper.getImageCountAll();
    }

}
