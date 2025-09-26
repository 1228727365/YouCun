package com.api.you_cun.tool;

import com.api.you_cun.controller.FileController;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 验证码生成工具类
 */
public class Tool {


    /**
     * 验证文件是否为真实图片（通过魔数判断）
     * 魔数参考：https://en.wikipedia.org/wiki/List_of_file_signatures
     */
    public static boolean isValidImageFile(MultipartFile file) throws IOException {
        Map<String, byte[]> imageMagicNumbers = new HashMap<>();
        imageMagicNumbers.put("jpg", new byte[]{(byte) 0xFF, (byte) 0xD8}); // JPG/JPEG
        imageMagicNumbers.put("png", new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47}); // PNG
        imageMagicNumbers.put("bmp", new byte[]{(byte) 0x42, (byte) 0x4D}); // BMP
        imageMagicNumbers.put("webp", new byte[]{(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46}); // WebP
        imageMagicNumbers.put("jfif", new byte[]{(byte) 0x4A, (byte) 0x46, (byte) 0x49, (byte) 0x46}); //JFIF
        String originalFilename = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if ("blob".equals(originalFilename)) {
            fileExt = getExtensionFromContentType(file.getContentType());
        }
        if (!imageMagicNumbers.containsKey(fileExt)) {
            return false;
        }
        byte[] magicNumber = imageMagicNumbers.get(fileExt);
        try (InputStream is = file.getInputStream()) {
            byte[] buffer = new byte[magicNumber.length];
            int read = is.read(buffer);
            if (read != magicNumber.length) {
                return false;
            }
            return Arrays.equals(buffer, magicNumber);
        }
    }


    /****
     * 时间戳转换
     */
    public static String gettimechu(Long timestamp){
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dateTime.format(formatter);
        return formattedTime;
    }


    public static String getCode() {
        Random random = new Random();
        int codeNum = random.nextInt(900000) + 100000;
        return String.valueOf(codeNum);
    }

    /**
     * 当前时间戳生成
     */
    public static String getTime(int times) {
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        //将时间推后5分钟
        long timeLong = Long.parseLong(time);
        time= String.valueOf(timeLong += times);
        return time;
    }

    /**
     * 当前时间到yyyy-MM-dd HH:mm:ss
     */
    public static String getFormattedTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }



    /**
     * 判断邮箱是否正确
     */
    public static boolean isMail(String mail) {
        String regex = "^\\w+@\\w+\\.\\w+$";
        return mail.matches(regex);
    }


    /****
     * 生成32位token，然后使用md5加密
     */
// 生成32位随机token
    public static String Token() {
        // 字符集：包含大小写字母和数字
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder(32);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 32; i++) {
            // 随机选择一个字符
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }

        return md5Encrypt(token.toString());
    }

    // MD5加密
    public static String md5Encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在", e);
        }
    }

/*
密码复杂度不能少于5位，包含字母数字
 */
    public static boolean isPassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,}$";
        return password.matches(regex);
    }



    public String getSiblingUploadDir(String file) {
        try {
            String classPath = FileController.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath();
            classPath = URLDecoder.decode(classPath, "UTF-8");
            File currentClassFile = new File(classPath);
            File currentDir = currentClassFile.getParentFile();
            File parentDir = currentDir.getParentFile();
            File uploadDir = new File(parentDir, file);
            return uploadDir.getAbsolutePath() + File.separator;

        } catch (Exception e) {
            return new File(file).getAbsolutePath() + File.separator;
        }
    }


    // 根据文件后缀获取对应的MIME类型
    public static String getContentType(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "webp":
                return "image/webp";
            case "bmp":
                return "image/bmp";
            default:
                return "application/octet-stream"; // 默认二进制流
        }
    }


    public static String getExtensionFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        switch (contentType) {
            case "image/jpeg":
            case "image/jpg":
                return "jpg";
            case "image/png":
                return "png";
            case "image/bmp":
                return "bmp";
            case "image/webp":
                return "webp";
            default:
                return null;
        }
    }

}
