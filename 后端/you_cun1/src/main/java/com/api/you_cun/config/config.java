package com.api.you_cun.config;

public class config {

    //管理员密码
    public static final String admin_password ="mw200464";
    //网站配置
    public static final String wz = "http://api.maolinux.top";
    //设置项目文件夹
    public static final String UPLOAD_PATH = System.getProperty("user.dir") + "/upload/";

    public static final class FileSize {
        public static final int NORMAL = 5242880;  // 普通用户空间
        public static final int VIP = 10485760;    // VIP用户空间
    }
    // 允许上传的文件格式
    public static final String[] allowedExts = {"jpg", "jpeg", "png", "webp"};
    // 单张图片最大内存最大内存限制（单位：字节，5MB）
    public static final long MAX_SIZE = 5242880;
}
