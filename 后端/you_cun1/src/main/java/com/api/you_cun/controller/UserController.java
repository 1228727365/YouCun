package com.api.you_cun.controller;

import ch.qos.logback.core.model.Model;
import com.api.you_cun.config.config;
import com.api.you_cun.mapper.CodeMapper;
import com.api.you_cun.pojo.Caimi;
import com.api.you_cun.pojo.Code;
import com.api.you_cun.pojo.User;
import com.api.you_cun.pojo.Userinfo;
import com.api.you_cun.service.CaimiService;
import com.api.you_cun.service.CodeService;
import com.api.you_cun.service.UserService;
import com.api.you_cun.tool.Result;
import com.api.you_cun.tool.ResultUtil;
import com.api.you_cun.tool.Tool;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CodeService codeService;
    @Autowired
    CaimiService caimiService;
    @Resource
    @Autowired
    JavaMailSender sender;


    @RequestMapping("/regis")
    public Result regis(@RequestParam
                        @NotBlank(message = "邮箱格式不正确")
                        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                message = "邮箱格式不正确")
                        String mail,
                        @RequestParam
                        @NotBlank(message = "密码格式不正确")
                        @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,}$",
                                message = "密码太过简单")
                        String password,
                        @RequestParam
                        @NotBlank(message = "验证码格式不正确")
                        @Pattern(regexp = "^[0-9]{6}$",
                                message = "验证码格式不正确")
                        String code) {
        User user = userService.getUser(mail);
        if (user != null) {
            return ResultUtil.error("邮箱已被注册");
        }
        String get_code = codeService.getCode(mail);
        if (!code.equals(get_code)) {
            return ResultUtil.error("验证码错误");
        }
        Long times = Long.parseLong(Tool.getTime(0));
        Long time = Long.parseLong(codeService.getTime(mail));
        if (times > time) {
            return ResultUtil.error("验证码已过期");
        }
        int result = userService.insertUser(mail, password);
        if (result <= 0) {
            return ResultUtil.error("注册失败");
        }
        return ResultUtil.success("注册成功");

    }

    @RequestMapping("/login")
    public Result Login(@RequestParam
                        @NotBlank(message = "邮箱格式不正确")
                        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                message = "邮箱格式不正确")
                        String mail,
                        @RequestParam
                        @NotBlank(message = "密码格式不正确")
                        String password) {
        User user = userService.Login(mail, password);
        if (user == null) {
            return ResultUtil.error("账号密码错误");
        }
        if (!user.getPassword().equals(Tool.md5Encrypt(password))) {
            return ResultUtil.error("账号密码错误");
        }
        String vip_time = user.getVip_time();
        if (vip_time.equals("0") || Long.parseLong(vip_time) < Long.parseLong(Tool.getTime(0))) {
            vip_time = "会员已过期";
        } else {
            vip_time = Tool.gettimechu(Long.parseLong(vip_time));
        }
        Userinfo userinfo = new Userinfo(user.getMoreSize(), user.getUseSize(), user.getFileNumber(), user.getImgNumber(), vip_time);
        return ResultUtil.success("登录成功", user.getToken(), userinfo);
    }

    //更新Token
    @RequestMapping("/upToken")
    public Object upToken(@RequestParam
                          @NotBlank(message = "邮箱格式不正确")
                          @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                  message = "邮箱格式不正确")
                          String mail,
                          @RequestParam
                          @NotBlank(message = "密码格式不正确")
                          String password,
                          @RequestParam
                          @NotBlank(message = "token格式不正确")
                          String Token) {
        User user = userService.getUserByToken(Token, mail);
        if (user == null) {
            return ResultUtil.error("用户不存在");
        }
        if (!Tool.md5Encrypt(password).equals(user.getPassword()) || !Token.equals(user.getToken())) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        String up_token = Tool.Token();
        int relus = userService.updataToken(up_token, user.getId());
        if (relus <= 0) {
            return ResultUtil.error("更新token失败");
        }
        return ResultUtil.success("更新token成功", up_token);
    }


    @RequestMapping("/getUserData")
    public Object getUserData(@RequestParam
                              @NotBlank(message = "邮箱格式不正确")
                              @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                      message = "邮箱格式不正确")
                              String mail,
                              @RequestParam
                              @NotBlank(message = "token格式不正确")
                              String token) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        String vip_time = user.getVip_time();
        if (vip_time.equals("0") || Long.parseLong(vip_time) < Long.parseLong(Tool.getTime(0))) {
            vip_time = "会员已过期";
        } else {
            vip_time = Tool.gettimechu(Long.parseLong(vip_time));
        }
        Userinfo userinfo = new Userinfo(user.getMoreSize(), user.getUseSize(), user.getFileNumber(), user.getImgNumber(), vip_time);
        return ResultUtil.success("获取用户数据成功", userinfo);
    }


    @RequestMapping("/updataPassword")
    public Object updataPassword(@RequestParam
                                 @NotBlank(message = "邮箱格式不正确")
                                 @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                         message = "邮箱格式不正确")
                                 String mail,
                                 @RequestParam
                                 @NotBlank(message = "token格式不正确")
                                 String Token,
                                 @RequestParam
                                 @NotBlank(message = "密码格式不正确")
                                 String password,
                                 @RequestParam
                                 @NotBlank(message = "新密码格式不正确")
                                 @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,}$",
                                         message = "新密码太过简单")
                                 String newPassword) {

        if (password.equals(newPassword)) {
            return ResultUtil.error("不能设置重复密码");
        }
        User user = userService.getUserByToken(Token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        if (!Tool.md5Encrypt(password).equals(user.getPassword())) {
            return ResultUtil.error("原始密码错误");
        }
        int result = userService.updataPassword(Tool.md5Encrypt(newPassword), user.getId());
        if (result <= 0) {
            return ResultUtil.error("修改密码失败");
        }
        return ResultUtil.success("修改密码成功");
    }


    //使用卡密
    @RequestMapping("/userCaimi")
    public Object userCaimi(@RequestParam
                            @NotBlank(message = "邮箱格式不正确")
                            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                    message = "邮箱格式不正确")
                            String mail,
                            @RequestParam
                            @NotBlank(message = "token格式不正确")
                            String token,
                            @RequestParam
                            @NotBlank(message = "卡密格式不正确")
                            String km) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        Caimi caimi_data = userService.getCaimi(km);
        if (caimi_data == null) {
            return ResultUtil.error("卡密不存在");
        }
        if (caimi_data.getType().equals("vip")) {
            String vip_time = user.getVip_time();
            Long up_vip = Long.valueOf(vip_time) + Long.valueOf(caimi_data.getValue());
            if (vip_time.equals("0") || Long.parseLong(vip_time) < Long.parseLong(Tool.getTime(0))) {
                up_vip = Long.valueOf(Tool.getTime(0)) + Long.valueOf(caimi_data.getValue());
            }
            int result = userService.updataVip(up_vip, user.getId());
            if (result <= 0) {
                return ResultUtil.error("卡密使用失败");
            }
            int str = caimiService.deleteCaimi(caimi_data.getId());
            return ResultUtil.success("卡密使用成功");
        } else if (caimi_data.getType().equals("size")) {
            int result = userService.updateMoreSize(user.getId(), Long.valueOf(caimi_data.getValue()) * 1024 * 1024);

            if (result <= 0) {
                return ResultUtil.error("卡密使用失败");
            }
            int str = caimiService.deleteCaimi(caimi_data.getId());
            return ResultUtil.success("卡密使用成功");
        } else {
            return ResultUtil.error("卡密类型错误");
        }

    }


    //忘记密码
    @RequestMapping("/sendForget")
    public Object sendForget(@RequestParam
                             @NotBlank(message = "邮箱格式不正确")
                             @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                     message = "邮箱格式不正确")
                             String mail) {
        User user = userService.getUser(mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        String newCode = Tool.getCode();
        String ismail = setmail(mail, newCode, user.getToken());
        if (!ismail.contains("成功")) {
            return ResultUtil.error("发送失败" + ismail);
        }
        Code newCodeObj = new Code();
        Code existingCode = codeService.getCodeByMail(mail);
        String newTime = Tool.getTime(300);
        newCodeObj.setMail(mail);
        newCodeObj.setCode(newCode);
        newCodeObj.setTime(newTime);
        if (existingCode != null) {

            int result = codeService.updateCode(newCodeObj);
            if (result <= 0) {
                return ResultUtil.error("发送失败");
            }
            return ResultUtil.success("发送成功");
        }
        int result = codeService.insertCode(newCodeObj);
        if (result <= 0) {
            return ResultUtil.error("发送失败");
        }
        return ResultUtil.success("发送成功");
    }

    public String setmail(String mail, String code, String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【优存科技】忘记密码验证");
        message.setText("欢迎优存科技用户！请访问网站进行修改密码：" + config.wz + "/api/user/forget?mail=" + mail + "&token=" + token + "&code=" + code + " 链接有效期为5分钟，请尽快使用。");
        message.setTo(mail);
        message.setFrom("maojas@163.com");
        try {
            sender.send(message);
            return "成功";
        } catch (MailException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @RequestMapping("/forget")
    public Object forget(@RequestParam
                         @NotBlank(message = "邮箱格式不正确")
                         @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                 message = "邮箱格式不正确")
                         String mail,
                         @RequestParam
                         @NotBlank(message = "token格式不正确")
                         String token,
                         @RequestParam
                         @NotBlank(message = "验证码格式不正确")
                         @Pattern(regexp = "^[0-9]{6}$",
                                 message = "验证码格式不正确")
                         String code,
                         @RequestParam(required = false, defaultValue = "")
                         @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,}$",
                                 message = "新密码太过简单")
                         String newPassword) {
        User user = userService.getUserByToken(token, mail);
        if (user == null) {
            return ResultUtil.error("登陆信息错误，请重新登陆");
        }
        Code code_data = codeService.getCodeByMail(mail);
        if (code_data == null) {
            return ResultUtil.error("邮箱未注册");
        }
        String get_code = codeService.getCode(mail);
        if (!code.equals(get_code)) {
            return ResultUtil.error("验证码错误");
        }
        Long times = Long.parseLong(Tool.getTime(0));
        Long time = Long.parseLong(codeService.getTime(mail));
        if (times > time) {
            return ResultUtil.error("验证码已过期");
        }

        if (newPassword == null || newPassword.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("password");
            modelAndView.addObject("mail", mail);
            modelAndView.addObject("token", token);
            modelAndView.addObject("code", code);
            return modelAndView;
        }
        int result = userService.updataPassword(Tool.md5Encrypt(newPassword), user.getId());
        if (result <= 0) {
            return ResultUtil.error("修改密码失败");
        }
        int str = codeService.updateTime(Tool.getTime(0), mail);
        if (str <= 0) {
            return ResultUtil.error("验证码更新失败");
        }
        return ResultUtil.success("修改密码成功");
    }
}
