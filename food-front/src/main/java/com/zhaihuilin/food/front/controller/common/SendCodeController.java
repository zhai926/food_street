package com.zhaihuilin.food.front.controller.common;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.constant.SmsConstant;
import com.zhaihuilin.food.code.entity.common.SmsCode;
import com.zhaihuilin.food.code.utils.GenerateSmsUtils;
import com.zhaihuilin.food.code.utils.SmsSendUtils;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.front.config.JedisUtils;
import com.zhaihuilin.food.front.config.SendEmailUtils;
import com.zhaihuilin.food.service.common.SmsCodeService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送相关信息  【短信 , 邮件 】
 * Created by zhaihuilin on 2018/12/28 11:45.
 */
@RestController
@RequestMapping("/api/sendCode/")
@Log4j
public class SendCodeController {


  @Autowired
  private SmsCodeService smsCodeService;

  @Autowired
  private SendEmailUtils sendEmailUtils;

  @Autowired
  private JedisUtils jedisUtils;


  /**
   * 点击获取验证码  【 手机号方式 】
   * @param phone
   * @return
   */
     @RequestMapping(value = "/sendSmsByPhone")
     public ReturnMessages  sendSmsByPhone(
          @RequestParam(name = "phone",required = true) String phone
     ){
         ReturnMessages returnMessages = null;
         SmsCode smsCode;
         if (!StringUtils.isNotEmpty(phone)){
           returnMessages = new ReturnMessages(RequestState.ERROR,"手机号不能为空",null);
           return  returnMessages;
         }else{
           String Code= GenerateSmsUtils.generateSms();//生成随机产生的验证码
           String contant = phone.concat(SmsConstant.L_CONTANT.concat(Code.concat(SmsConstant.R_CONTANT)));
           HashMap<String, String> pp = (HashMap<String, String>) SmsSendUtils.singleMt(phone, contant);
           Map<String, Object> map = new HashMap<String,Object>();
           try {
             if (pp !=null){
               smsCode=new SmsCode(pp.get("mtmsgid"),SmsConstant.SA,phone,Code,pp.get("mtstat"),pp.get("mterrcode"));
               // 保存操作
               smsCode=smsCodeService.saveSmsCode(smsCode);
               // 将其存储到 redis 中
               jedisUtils.set(phone,Code,SmsConstant.AVAILABLE_PERIOD);
               map.put("code", Code);
               if (smsCode !=null){
                 map.put("smsCode",smsCode);
                 return  new ReturnMessages(RequestState.SUCCESS,"发送成功！",map);
               }else {
                 return  new ReturnMessages(RequestState.ERROR,"发送失败！",null);
               }
             }
             return  new ReturnMessages(RequestState.ERROR,"发送失败！",null);
           }catch (Exception e){
             return  new ReturnMessages(RequestState.ERROR,"发送失败！",null);
           }
         }
     }


  /**
   * 通过邮件的方式发送验证码
   * @param eMail
   * @return
   */
  @RequestMapping(value = "/sendSmsByEmail")
     public  ReturnMessages sendSmsByEmail(
             @RequestParam(name = "email",required = true) String eMail
     ){
       ReturnMessages returnMessages;
       if (!StringUtils.isNotEmpty(eMail)){
         returnMessages = new ReturnMessages(RequestState.ERROR,"邮箱不能为空",null);
         return  returnMessages;
       }else{
         String Code= GenerateSmsUtils.generateSms();//生成随机产生的验证码
         //开始发送邮件
         sendEmailUtils.sendRegisterCode(eMail,Code);
         // 将其存储到 redis 中
         jedisUtils.set(eMail,Code,SmsConstant.AVAILABLE_PERIOD);
         returnMessages = new ReturnMessages(RequestState.SUCCESS,"发送成功!",null);
         return  returnMessages;
       }
     }
}
