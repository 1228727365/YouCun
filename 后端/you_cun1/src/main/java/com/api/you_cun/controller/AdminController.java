package com.api.you_cun.controller;


import com.api.you_cun.config.config;
import com.api.you_cun.pojo.Caimi;
import com.api.you_cun.service.CaimiService;
import com.api.you_cun.service.FileService;
import com.api.you_cun.service.ImageService;
import com.api.you_cun.service.UserService;
import com.api.you_cun.tool.ResultUtil;
import com.api.you_cun.tool.Tool;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@Validated
@CrossOrigin
public class AdminController {
    @Autowired
    CaimiService caimiService;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @Autowired
    FileService fileService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/addCaimi")

    public Object addCaimi(@RequestParam
                           @NotBlank(message = "请输入管理员密码")
                           @Pattern(regexp = config.admin_password, message = "管理员密码错误")
                           String admin,
                           @RequestParam
                           @NotBlank(message = "类型type不能为空")
                           @Pattern(regexp = "^(size|vip)$", message = "类型type只能为size和vip")
                           String type,
                           @RequestParam
                           @NotBlank(message = "请输入卡密value")
                           String value,
                           @RequestParam(required = false, defaultValue = "1")
                           @Min(value = 1, message = "数量number必须大于0")
                           Integer number) {

        Object km_text = "";
        List<Map<String, String>> caimilist = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String uniqueInput = Tool.getTime(0) + "_" + System.currentTimeMillis() + "_" + new Random().nextInt(10000) + "_" + i;
            String km = Tool.md5Encrypt(uniqueInput).substring(16);
            Map<String, String> caimi = new HashMap<>();
            caimi.put("km", km);
            caimi.put("value", value);
            caimi.put("type", type);
            caimilist.add(caimi);
            km_text = km_text + km + "<br>";
        }
        int result = caimiService.addCaimi(caimilist);
        if (result != number) {
            return ResultUtil.success("卡密添加失败");
        }
        return ResultUtil.success("卡密添加成功", km_text);
    }

    @RequestMapping("/getCaimiAll")
    public Object getCaimiAll(@RequestParam
                              @NotBlank(message = "请输入管理员密码")
                              @Pattern(regexp = config.admin_password, message = "管理员密码错误")
                              String admin) {
        List<Caimi> caimiList = caimiService.getCaimiAll();
        if (caimiList == null) {
            return ResultUtil.error("获取卡密失败");
        }
        return ResultUtil.success("获取卡密成功", caimiList);

    }


    @RequestMapping("/Count")
    public Object Count() {
        int user_count = userService.getUserCountAll();
        int image_count = imageService.getImageCountAll();
        int file_count = fileService.getFileCountAll();
        Map<String, Object> map = new HashMap<>();
        map.put("user_count", user_count);
        map.put("image_count", image_count);
        map.put("file_count", file_count);
        return ResultUtil.success("获取数据成功", map);
    }


    @RequestMapping("/API")
    public Object API() {
        String filePath = System.getProperty("user.dir") + "/api.json";

        File file = new File(filePath);
        if (!file.exists()) {
            return ResultUtil.error("获取API失败：api.json文件不存在");
        }
        if (!file.isFile()) {
            return ResultUtil.error("获取API失败：路径指向的不是文件");
        }
        try {
            Object jsonData = objectMapper.readValue(Paths.get(filePath).toFile(), Object.class);
            return jsonData;

        } catch (Exception e) {
            return ResultUtil.error("获取API失败：" + e.getMessage());
        }
    }
}
