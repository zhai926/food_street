package com.zhaihuilin.food.front.controller.common;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.front.config.JedisUtils;
import com.zhaihuilin.food.service.member.MemberService;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 * Created by zhaihuilin on 2018/12/28 16:02.
 */
@RestController
@RequestMapping("/api")
@Log4j
//@Api(value = "登录登出相关接口",tags = {"登录登出相关接口"})
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JedisUtils jedisUtils;

  /**
   * 登录  【 有两种方式   1. 手机动态验证登录方式   2. 输入密码的方式 】
   * @param phone    手机号
   * @param password   密码
   * @param code     动态验证码
   * @param request
   * @return
   */
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
    //        @ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String"),
    //        @ApiImplicitParam(name = "code", value = "手机动态验证", required = false, dataType = "String")
    //})
    @PostMapping(value = "/login")
    //@ApiOperation(value = "登录",notes = "可以进行密码或者手机验证码动态登录")  // 描述一个类的一个方法，或者说一个接口
    public ReturnMessages  Login(
        @RequestParam(value = "phone") String phone,
        @RequestParam(value = "password",required = false) String password,
        @RequestParam(value = "code",required = false) String code,
        HttpServletRequest request
    ){
        ReturnMessages returnMessages;
        try {
          if (!StringUtils.isNotEmpty(phone)){
            returnMessages = new ReturnMessages(RequestState.ERROR,"手机号不能为空！",null);
            return returnMessages;
          }
          Member  member=memberService.findMemberByPhone(phone);
          if (member==null){
            returnMessages = new ReturnMessages(RequestState.ERROR,"手机号未注册,请注册后再使用！",null);
            return returnMessages;
          }
          // 当密码不为空时
          if (StringUtils.isNotEmpty(password)){

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
            try {
              subject.login(token);
              returnMessages = new ReturnMessages(RequestState.SUCCESS, "登录成功！", member);
              return returnMessages;
            } catch (IncorrectCredentialsException e) {
              returnMessages = new ReturnMessages(RequestState.ERROR, "密码错误！", null);
              return returnMessages;
            } catch (LockedAccountException e) {
              returnMessages = new ReturnMessages(RequestState.ERROR, "登录失败，该用户已被冻结！", null);
              return returnMessages;
            } catch (AuthenticationException e) {
              returnMessages = new ReturnMessages(RequestState.ERROR, "登录失败，该用户不存在！", null);
              return returnMessages;
            } catch (Exception e) {
              log.info("登录失败的原因:" + e.getMessage());
              e.printStackTrace();
            }

            return  null;
          }
         // 当以验证码登录时
          if (StringUtils.isNotEmpty(code)){
            // 获取redis存储的code 值
             String redis_code = jedisUtils.get(phone);
             if (!StringUtils.isNotEmpty(redis_code)){
                returnMessages= new ReturnMessages(RequestState.ERROR,"验证码已过期",null);
                return  returnMessages;
              }
             if (!redis_code.equals(code)){
              returnMessages= new ReturnMessages(RequestState.ERROR,"验证码输入有误",null);
              return  returnMessages;
             }
            try {
              returnMessages = new ReturnMessages(RequestState.SUCCESS,"登录成功！",member);
              return returnMessages;
            }catch (Exception e){
              e.printStackTrace();
              log.info("登录失败的原因:" + e.getMessage());
              returnMessages = new ReturnMessages(RequestState.ERROR,"登录失败！",null);
              return returnMessages;
            }
          }

        }catch (Exception e){
          e.printStackTrace();
          log.info("登录失败的原因:" + e.getMessage());
          returnMessages = new ReturnMessages(RequestState.ERROR,"登录失败！",null);
          return returnMessages;
        }
        return null;
    }


  /**
   * 登录2
   * @param username
   * @param password
   * @param request
   * @return
   */
  //@ApiImplicitParams({
  //        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
  //        @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
  //})
  //@ApiOperation(value = "登录另一种方式",notes = "用户名和密码登录")  // 描述一个类的一个方法，或者说一个接口
  @PostMapping(value = "/login2")
  public ReturnMessages  Login2(
          @RequestParam(value = "username",required = true) String username,
          @RequestParam(value = "password",required = true) String password,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages;
      if ((!StringUtils.isNotEmpty(username)) || (!StringUtils.isNotEmpty(password))) {
        returnMessages = new ReturnMessages(RequestState.ERROR, "账户或密码不能为空！", null);
        return returnMessages;
      }
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
      try {
        subject.login(token);
        returnMessages = new ReturnMessages(RequestState.SUCCESS, "登录成功！", username);
        return returnMessages;
      } catch (IncorrectCredentialsException e) {
        returnMessages = new ReturnMessages(RequestState.ERROR, "密码错误！", null);
        return returnMessages;
      } catch (LockedAccountException e) {
        returnMessages = new ReturnMessages(RequestState.ERROR, "登录失败，该用户已被冻结！", null);
        return returnMessages;
      } catch (AuthenticationException e) {
        returnMessages = new ReturnMessages(RequestState.ERROR, "登录失败，该用户不存在！", null);
        return returnMessages;
      } catch (Exception e) {
        log.info("登录失败的原因:" + e.getMessage());
        e.printStackTrace();
      }
    return  null;
  }



  /**
   * 登出
   * @return
   */
  @PostMapping(value = "/logout")
  //@ApiOperation(value = "登出",notes = "登出接口")  // 描述一个类的一个方法，或者说一个接口
  public ReturnMessages  logout(){
    ReturnMessages returnMessages;
    try {
       Subject subject = SecurityUtils.getSubject();
       if (subject!=null){
           subject.logout();
           returnMessages = new ReturnMessages(RequestState.SUCCESS,"登出成功！",null);
           return returnMessages;
       }
    }catch (Exception e){
      returnMessages = new ReturnMessages(RequestState.ERROR,"登出失败！",null);
      return returnMessages;
    }
    return  null;
  }

}
