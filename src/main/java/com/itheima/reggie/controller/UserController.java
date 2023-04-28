package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.SMSUtils;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone=user.getPhone();


        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            //调用阿里云提供的短信服务API完成发送短信
            //需要将生成的验证码保存到session
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(phone);
            log.info(code);
            //SMSUtils.sendMessage("菜帅","SMS_276496166",phone,code);
            session.setAttribute(phone,code);
            return R.success("短信发送成功");
        }

        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号与验证码
        String phone=map.get("phone").toString();
        String code=map.get("code").toString();
        //从session获得验证码
        Object codeInSession = session.getAttribute(phone);
        //进行比对
        if(codeInSession!=null&&codeInSession.equals(code)){

            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                user=new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("验证码错啦");
    }
}
