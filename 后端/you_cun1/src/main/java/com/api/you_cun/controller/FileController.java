package com.api.you_cun.controller;

import com.api.you_cun.pojo.File;
import com.api.you_cun.pojo.User;
import com.api.you_cun.service.FileService;
import com.api.you_cun.service.UserService;
import com.api.you_cun.tool.AES128CBC;
import com.api.you_cun.tool.Result;
import com.api.you_cun.tool.ResultUtil;
import com.api.you_cun.tool.Tool;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
public class FileController {
    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;


    //新建文档
    @RequestMapping("/newfile")
    public Result newfile(@RequestParam
                          @NotBlank(message = "token格式不正确")
                          String token,
                          @RequestParam
                          @NotBlank(message = "邮箱格式不正确")
                          @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                  message = "邮箱格式不正确")
                          String mail,
                          @RequestParam(required = false, defaultValue = "")
                          String content,
                          @RequestParam(required = false, defaultValue = "")
                          String password) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        int uid = user.getId();
        String date = Tool.getFormattedTime();
        String Ftokne = Tool.Token();
        int result = fileService.insertFile(uid, content, date, Ftokne, 1, password, "");
        if (result <= 0) {
            return ResultUtil.error("创建文档失败");
        }
        int result1 = userService.addFileNumber(uid);
        return ResultUtil.success("创建文档成功", Ftokne);
    }


    //设置文档tokne
    @RequestMapping("/setFileToken")
    public Result setfiletoken(@RequestParam
                               @NotBlank(message = "邮箱格式不正确")
                               @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                       message = "邮箱格式不正确")
                               String mail,
                               @RequestParam
                               @NotBlank(message = "token格式不正确")
                               String token,
                               @RequestParam
                               @NotBlank(message = "Ftoken格式不正确")
                               @Pattern(regexp = "^[a-zA-Z0-9]{32}$",
                                       message = "文档Ftoken格式不正确")
                               String Ftoken,
                               @RequestParam
                               @NotNull(message = "Fid格式不正确")
                               Integer Fid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        int result = fileService.setFileToken(Fid, Ftoken);
        if (result <= 0) {
            return ResultUtil.error("设置文档Ftokne失败");
        }
        return ResultUtil.success("设置文档Ftokne成功", Ftoken);
    }

    //设置文档密码
    @RequestMapping("/setFilePassword")
    public Result setfilepassword(@RequestParam
                                  @NotBlank(message = "邮箱格式不正确")
                                  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                          message = "邮箱格式不正确")
                                  String mail,
                                  @RequestParam
                                  @NotBlank(message = "token格式不正确")
                                  String token,
                                  @RequestParam(required = false, defaultValue = "")
                                  String Fpassword,
                                  @RequestParam
                                  @NotNull(message = "Fid格式不正确")
                                  Integer Fid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        int result = fileService.setFilePassword(Fid, Fpassword);
        if (result <= 0) {
            return ResultUtil.error("设置文档密码失败");
        }
        return ResultUtil.success("设置文档密码成功");

    }

    //设置文档AesKey
    @RequestMapping("/setFileAesKey")
    public Result setfileAesKey(@RequestParam
                                @NotBlank(message = "邮箱格式不正确")
                                @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                        message = "邮箱格式不正确")
                                String mail,
                                @RequestParam
                                @NotBlank(message = "token格式不正确")
                                String token,
                                @RequestParam(required = false, defaultValue = "")
                                @Pattern(regexp = "^$|^.{16}$", message = "AesKey为空或必须是16位长度")
                                String AesKey,
                                @RequestParam
                                @NotNull(message = "Fid格式不正确")
                                @Min(value = 1, message = "Fid必须大于0")
                                Integer Fid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        if (user.getVip_time().equals("0") || Long.parseLong(user.getVip_time()) < Long.parseLong(Tool.getTime(0))) {
            return ResultUtil.error("会员才能使用该功能");
        }
        int result = fileService.setFileAesKey(Fid, AesKey);
        if (result <= 0) {
            return ResultUtil.error("设置文档AesKey失败");
        }
        return ResultUtil.success("设置文档AesKey成功");

    }

    //设置文档状态
    @RequestMapping("/setFileState")
    public Result setFileState(@RequestParam
                               @NotBlank(message = "邮箱格式不正确")
                               @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                       message = "邮箱格式不正确")
                               String mail,
                               @RequestParam
                               @NotBlank(message = "token格式不正确")
                               String token,
                               @RequestParam
                               @NotNull(message = "state格式不正确")
                               @Range(min = 0, max = 1, message = "state状态只能为0或1")
                               Integer state,
                               @RequestParam
                               @NotNull(message = "Fid格式不正确")
                               @Min(value = 1, message = "Fid必须大于0")
                               Integer Fid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        if (user.getVip_time().equals("0") || Long.parseLong(user.getVip_time()) < Long.parseLong(Tool.getTime(0))) {
            return ResultUtil.error("会员才能使用该功能");
        }
        int result = fileService.setFileState(Fid, state);
        if (result <= 0) {
            return ResultUtil.error("关闭文档失败");
        }
        if (state.equals(0)) {
            return ResultUtil.success("关闭文档成功");
        }
        return ResultUtil.success("开启文档成功");
    }

    //查询文档
    @RequestMapping("/getFile")
    public Object getFile(@RequestParam
                          @NotBlank(message = "Ftoken格式不正确")
                          @Pattern(regexp = "^[a-zA-Z0-9]{32}$",
                                  message = "文档Ftoken要求32位")
                          String Ftoken,
                          @RequestParam(required = false, defaultValue = "")
                          String Fpassword,
                          @RequestParam(required = false, defaultValue = "text")
                          @Pattern(regexp = "^(json|text)$", message = "type格式为text/json")
                          String type) {
        File file_data = fileService.getFile(Ftoken);
        if (file_data == null) {
            return ResultUtil.error("文档不存在");
        }
        if (file_data.getState() == 0) {
            return ResultUtil.error("文档已关闭");
        }
        if (file_data.getPassword().equals("")) {
            return getContent(type, file_data);
        }
        if (Fpassword == null || Fpassword.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("wd_password");
            modelAndView.addObject("Ftoken", Ftoken);
            modelAndView.addObject("type", type);
            return modelAndView;
        }
        if (!file_data.getPassword().equals(Fpassword)) {
            return ResultUtil.error("密码错误");
        }
        return getContent(type, file_data);

    }

    //获取文档内容
    private Object getContent(String type, File file_data) {
        if (type.equals("json")) {
            if (file_data.getAesKey().equals("")) {
                return ResultUtil.success_content("获取文档成功", file_data.getContent());
            }
            String AES_content = AES128CBC.encrypt(file_data.getAesKey(), file_data.getContent());
            return ResultUtil.success_content("获取文档成功", AES_content);
        }
        if (file_data.getAesKey().equals("")) {
            return file_data.getContent();
        }
        String AES_content = AES128CBC.encrypt(file_data.getAesKey(), file_data.getContent());
        return AES_content;
    }


    //查询所有文档
    @RequestMapping("/getAllFile")
    public Object getAllFile(@RequestParam
                             @NotBlank(message = "token格式不正确")
                             String token,
                             @RequestParam
                             @NotBlank(message = "邮箱格式不正确")
                             @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                     message = "邮箱格式不正确")
                             String mail,
                             @RequestParam(required = false, defaultValue = "1")
                             @NotNull(message = "page格式不正确")
                             @Min(value = 1, message = "page必须大于0")
                             Integer page) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        File[] file_data = fileService.getFilesByPage(String.valueOf(user.getId()), page);
        return ResultUtil.success("获取文档成功", file_data);
    }

    //删除文档
    @RequestMapping("/deleteFile")
    public Result deleteFile(@RequestParam
                             @NotBlank(message = "token格式不正确")
                             String token,
                             @RequestParam
                             @NotBlank(message = "邮箱格式不正确")
                             @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                     message = "邮箱格式不正确")
                             String mail,
                             @RequestParam
                             @NotNull(message = "FId格式不正确")
                             @Min(value = 1, message = "FId必须大于0")
                             Integer Fid) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        int result = fileService.deleteFile(Fid, user.getId());
        if (result <= 0) {
            return ResultUtil.error("删除文档失败");
        }
        int result1 = userService.subFileNumber(user.getId());
        return ResultUtil.success("删除文档成功");
    }

    //修改文档
    @RequestMapping("/updateFile")
    public Result updateFile(@RequestParam
                             @NotBlank(message = "token格式不正确")
                             String token,
                             @RequestParam
                             @NotBlank(message = "邮箱格式不正确")
                             @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                     message = "邮箱格式不正确")
                             String mail,
                             @RequestParam
                             @NotNull(message = "Fid格式不正确")
                             @Min(value = 1, message = "Fid必须大于0")
                             Integer Fid,
                             @RequestParam(required = false, defaultValue = "")
                             String content) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        int result = fileService.updateFile(Fid, content, user.getId());
        int result1 = fileService.updateFileDate(Fid);
        if (result <= 0 && result1 <= 0) {
            return ResultUtil.error("修改文档失败");
        }
        return ResultUtil.success("修改文档成功");
    }
}
