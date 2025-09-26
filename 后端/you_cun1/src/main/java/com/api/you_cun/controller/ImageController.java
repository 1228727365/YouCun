package com.api.you_cun.controller;


import com.api.you_cun.config.config;
import com.api.you_cun.pojo.Image;
import com.api.you_cun.pojo.User;
import com.api.you_cun.service.ImageService;
import com.api.you_cun.service.UserService;
import com.api.you_cun.tool.ResultUtil;
import com.api.you_cun.tool.Tool;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

import static com.api.you_cun.config.config.UPLOAD_PATH;
import static com.api.you_cun.config.config.allowedExts;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
public class ImageController {

    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;


    @PostMapping("/imgUpload")
    public Object imgUpload(@RequestParam
                            @NotBlank(message = "邮箱格式不正确")
                            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                    message = "邮箱格式不正确")
                            String mail,
                            @RequestParam
                            @NotBlank(message = "token格式不正确")
                            String token,
                            @RequestParam
                            @NotNull(message = "请选择要上传的图片")
                            MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        System.out.println("上传文件原始名：" + originalFilename);
        boolean isBlobFile = "blob".equals(originalFilename);
        if (originalFilename == null || originalFilename.trim().isEmpty() ||
                (!isBlobFile && !originalFilename.contains("."))) {
            return ResultUtil.error("请上传有效的图片文件");
        }
        String fileExt;
        if (isBlobFile) {
            fileExt = Tool.getExtensionFromContentType(file.getContentType());
            if (fileExt == null) {
                return ResultUtil.error("无法识别图片格式，请上传标准图片文件");
            }
        }
        fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // 允许的图片扩展名
        if (!Arrays.asList(allowedExts).contains(fileExt)) {
            return ResultUtil.error("只允许上传图片格式文件（" + String.join(",", allowedExts) + "）");
        }
        // 文件魔数验证
        try {
            if (!Tool.isValidImageFile(file)) {
                return ResultUtil.error("文件内容不符合图片格式，请上传真实图片");
            }
        } catch (IOException e) {
            return ResultUtil.error("文件验证失败：" + e.getMessage());
        }

        if (file.getSize() > config.MAX_SIZE) {
            return ResultUtil.error("文件大小超出5MB");
        }
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        Long set_size;
        if (!user.getVip_time().equals("0") || Long.parseLong(user.getVip_time()) >= Long.parseLong(Tool.getTime(0))) {
            set_size = (long) config.FileSize.VIP;

        }
        set_size = (long) config.FileSize.NORMAL;
        Long data_size = Long.valueOf(user.getMoreSize()) + set_size;
        Long user_size = Long.valueOf(user.getUseSize()) + file.getSize();
        if (data_size <= user_size) {
            return ResultUtil.error("内存已到达上限，请扩展内存");
        }
        try {
            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Random random = new Random();
            int number = random.nextInt(90000) + 10000;
            String fileName = Tool.getTime(0) + number + "." + fileExt;
            File saveFile = new File(UPLOAD_PATH + fileName);
            file.transferTo(saveFile);

            int uid = user.getId();
            int insertResult = imageService.insertImage(uid, fileName, file.getSize() + "", Tool.getFormattedTime());
            if (insertResult <= 0) {
                return ResultUtil.error("图片上传失败");
            }
            int updateImgCount = userService.addImgNumber(uid);
            int updateSize = userService.updateUserSize(uid, file.getSize());
            return ResultUtil.success("图片上传成功");
        } catch (IOException e) {
            return ResultUtil.error("图片上传异常：" + e.getMessage());
        }
    }


    //获取图片
    @RequestMapping("/getImage")
    public Object getImage(@RequestParam
                           @NotNull(message = "图片Tid不能为空")
                           Integer Tid) {
        String str = imageService.getImage(Tid);
        if (str == null) {
            return ResultUtil.error("图片不存在");
        }
        try {
            Path imagePath = Paths.get(UPLOAD_PATH, str);
            File imageFile = imagePath.toFile();
            if (!imageFile.exists() || !imageFile.canRead()) {
                return ResultUtil.error("图片文件不存在或无法读取");
            }
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = Tool.getContentType(str);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error("图片读取失败：" + e.getMessage());
        }

    }

    //删除图片
    @RequestMapping("/DelImage")
    public Object DelImage(@RequestParam
                           @NotBlank(message = "邮箱格式不正确")
                           @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                   message = "邮箱格式不正确")
                           String mail,
                           @RequestParam
                           @NotBlank(message = "token格式不正确")
                           String token,
                           @RequestParam
                           @NotNull(message = "图片Tid不能为空")
                           @Min(value = 1, message = "图片Tid必须大于0")
                           Integer Tid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        String file_size = imageService.getImageSize(Tid);
        if (file_size == null) {
            return ResultUtil.error("图片不存在");
        }
        String file_name = imageService.getImageName(Tid);
        int str = imageService.DelImage(Tid, user.getId());

        if (str <= 0) {
            return ResultUtil.error("图片删除失败");
        }
        File file = new File(UPLOAD_PATH + file_name);
        if (!file.exists()) {
            return ResultUtil.error("图片不存在");
        }
        file.delete();
        int str1 = userService.subImgNumber(user.getId());
        int str2 = userService.subUserSize(user.getId(), Long.valueOf(file_size));
        return ResultUtil.success("图片删除成功");
    }


    //获取所有图片
    @RequestMapping("getImageAll")
    public Object getImageAll(@RequestParam
                              @NotBlank(message = "邮箱格式不正确")
                              @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                      message = "邮箱格式不正确")
                              String mail,
                              @RequestParam
                              @NotBlank(message = "token格式不正确")
                              String token,
                              @RequestParam(required = false, defaultValue = "1")
                              @Min(value = 1, message = "页码page必须大于0")
                              Integer page) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        Image[] file_data = imageService.getImageByPage(String.valueOf(user.getId()), page);
        return ResultUtil.success("获取图片成功", file_data);
    }
}