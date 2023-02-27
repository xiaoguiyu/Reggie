package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.User;
import com.xiaoyu.service.UserService;
import com.xiaoyu.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/15 20:56
 * 用户端请求处理
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 发送验证码短信
     * @param user 用户信息
     * @return 返回成功的消息
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {

        // 获取登录的手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {

            // 生成4位短信登录验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("生成的验证码为: " + code);

            // 使用阿里云短信服务, 向用户发送短信验证码  这里也可以使用邮箱来代替短信
            //SMSUtils.sendMessage("reggie", "", phone, code);

            // 将生成的验证码保存到session 中
            session.setAttribute(phone, code);
            return Result.success("短信发送成功!");
        }
        return Result.error("短信发送失败!");
    }

    /**
     * 登录
     * @param map 使用map来接收前端传递的验证码和手机号
     * @param session session
     * @return 成功或失败的信息
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map<String, Object> map, HttpSession session) {

        // 获取用户输入的手机号
        String phone = map.get("phone").toString();
        // 获取用户输入的验证码
        String code = map.get("code").toString();
        // 从session 中获取发送的验证码
        String codeInSession = session.getAttribute(phone).toString();


        // 登录的判断
        if (codeInSession != null && codeInSession.equals(code)) {

            // 查询此用户是否存在
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User user = userService.getOne(wrapper);

            // 判断此用户是否为新用户, 如果为新用户, 这保存用户的信息
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return Result.success(user);
        }
        return Result.error("登录失败!");
    }

    /**
     * 注销
     * @return 处理结果
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {

        // 移除session
        session.removeAttribute("user");
        return Result.success("退出登录成功!");
    }




}
