package com.kexun.api;

import com.kexun.pojo.Login;
import com.kexun.pojo.User;
import com.kexun.service.UserService;
import com.kexun.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@RestController
public class LoginApi {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailUtils emailUtils;


    /*获取公钥*/
    @GetMapping("getPublicKey")
    public Msg getPublicKey() {
        String publicKey = RSA.getPublicKey();

        Msg msg = new Msg().success("success").data("publicKey", publicKey);

        return msg;
    }

    /*获取验证码*/
    @GetMapping("getAuthCode")
    public void getAuthCode(HttpSession session, HttpServletResponse response) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        session.setAttribute("vcCode", vc.getText());//保存图片上的文本到session域
        VerifyCode.output(image, response.getOutputStream());
    }


    @PostMapping("login")
    public Msg login(Login login, HttpSession session) {
        String code = (String) session.getAttribute("vcCode");

        if (code != null) {
            if (code.equalsIgnoreCase(login.getCode())) {

                //加密解密操作
                try {
                    login.setPassword(MD5Util.entry(RSA.jiemi(login.getPassword())));
                } catch (Exception e) {
                }


                User user = userService.login(login);
                if (user != null) {
                    //移除验证码
                    session.removeAttribute("vcCode");
                    session.setAttribute("user", user);
                    return new Msg().success("登录成功").data("user", user);
                } else {
                    return new Msg().error("用户名或密码不正确！");
                }

            } else {
                return new Msg().error("验证码错误！");
            }
        } else {
            return new Msg().error("验证码错误！");
        }

    }

    @GetMapping("getEmailCode")
    public Msg getEmailCode(String email, HttpSession session) {

        //判断在一分钟之内用户是否已经发送过验证码
        String emailCode = (String) session.getAttribute("emailCode");
        if (emailCode != null) {
            return new Msg().error("验证码发送中,请耐心等待");
        }

        /*获取注册验证码*/
        int code = (int) ((Math.random() * 9 + 1) * 1000);
        /*验证邮箱是否正确*/
        boolean isMatch = Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", email);
        if (!isMatch) {
            return new Msg().error("邮箱格式错误");
        }

        /*消息模板*/
        String msg = "[优客商城] 验证码为:" + code + ",请在一分钟内输入验证码完成注册,感谢您对我们的支持";
        System.out.println(email);
        //调用消息服务发送短信验证码
        emailUtils.sendSimpleMail(email, "验证码", msg);
        session.setAttribute("emailCode", code + "");
        System.out.println("验证码>>>>>" + code);
        /*有效时间60s*/
        session.setMaxInactiveInterval(60);

        return new Msg().success("验证码发送成功,请耐心等待");
    }

    @PostMapping("register")
    public Msg register(User user, String code, HttpSession session, BindingResult bindingResult) {

        System.out.println(user);
        String mycode = (String) session.getAttribute("emailCode");

        //验证验证码
        if (StringUtils.isEmpty(code) || !code.equals(mycode)) {
            return new Msg().error("验证码错误,请重新输入");
        }

        //判断用户是否已近存在
        int userByUsername = userService.findUserByUsername(user.getUsername());
        System.out.println(user.getUsername());
        if (userByUsername > 0) {
            return new Msg().error("注册失败! 用户名已存在");
        }

        //对密码进行md5加密
        try {
            user.setPassword(MD5Util.entry(RSA.jiemi(user.getPassword())));
        } catch (Exception e) {

        }

        //生成userid
        Snowflake snowflake = new Snowflake(1, 5);
        long userid = snowflake.nextId();
        user.setUserid(userid);
        boolean register = userService.register(user);

        if (register) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return new Msg().success("注册成功");
        } else {
            return new Msg().error("注册失败");
        }


    }

    @GetMapping("logout")
    public Msg logout(HttpSession session) {
        session.removeAttribute("user");
        return new Msg().success("您已安全退出");
    }


}
