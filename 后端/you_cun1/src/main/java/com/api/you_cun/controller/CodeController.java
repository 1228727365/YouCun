package com.api.you_cun.controller;

import com.api.you_cun.pojo.Code;
import com.api.you_cun.service.CodeService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
public class CodeController {
    @Autowired
    CodeService codeService;
    @Resource
    @Autowired
    JavaMailSender sender;


    @RequestMapping("/code")
    public Object code(@RequestParam
                       @NotBlank(message = "邮箱格式不正确")
                       @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                               message = "邮箱格式不正确")
                       String mail) {
        String newCode = Tool.getCode();
        String ismail = setmail(newCode, mail);
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


    /*
    发送邮箱

     */
    public String setmail(String code, String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【优存科技】用户注册验证码");
        message.setText("欢迎优存科技用户！ 您的验证码为：" + code + " 有效期为5分钟，请尽快使用。");
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
}
