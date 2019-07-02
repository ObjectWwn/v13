package com.qianfeng.v13userweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.user.IUserService;
import com.qianfeng.v13.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * @Author wwn
 * @Date 2019/6/24
 */
@Controller
@RequestMapping("user")
public class UserController {

    private String form="1095838982@qq.com";



    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Reference
    private IUserService userService;

    //redis
    @Resource
    private RedisTemplate redisTemplate;

    //跳转页面
    @RequestMapping("register")
    public String setRegister(){
        return "register";
    }


    //注册
    @ResponseBody
    @RequestMapping("isRegister")
    public ResultBean isRegister(TUser user){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
       // String code =  redisTemplate.opsForValue().get("code")+"";
      //  System.out.println(user.getUsername());
        //主键回填
        int id = userService.insertSelective(user);
        //System.out.println("id"+id);
       // System.out.println(code);
       // System.out.println(user.getCode());
        //超时或输入不正确
       // if(code!=null && code.equals(user.getCode())){
            //发送激活邮件
            String uuid = UUID.randomUUID().toString();
            sendTemplateEmail(form,"active","uuid", uuid);
            //将id存入redis
            redisTemplate.opsForValue().set(uuid,id);
            return  new ResultBean("200","注册成功");
        //} else{
         //   return new ResultBean("404","注册失败，验证码错误");
      //  }
    }

    //激活
    @RequestMapping("active/{uuid}")
    public String activeById(@PathVariable(name = "uuid") String uuid, Model model){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int id = (int) redisTemplate.opsForValue().get(uuid);
        TUser user = new TUser();
        user.setId(id);
        user.setIsactivate(true);
        userService.updateByPrimaryKeySelective(user);
        model.addAttribute("uuid",uuid);
        return "redirect:http://localhost:9096/sso/showLogin";
    }

    //发送验证码
    @RequestMapping("verification/{Email}")
    @ResponseBody
    public ResultBean sendCodeByEmail(@PathVariable(name = "Email")  String email){
        //生成四位随机数
        int code = (int)((Math.random()*9+1)*1000);
        System.out.println("code"+code);
        //发送邮件
        sendTemplateEmail(email,"EmailTemplate","code",code+"");
        //将验证码存入redis
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set("code",code);
        return new ResultBean("200",code);
    }

    private void sendTemplateEmail(String to,String EmailTemplateName,String key,String value) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(form);
            helper.setTo(to);
            helper.setSubject("主题：模板邮件");

            Context context = new Context();
            context.setVariable(key,value);
            String emailContent = templateEngine.process(EmailTemplateName,context);

            helper.setText(emailContent,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        sender.send(mimeMessage);
    }


}
