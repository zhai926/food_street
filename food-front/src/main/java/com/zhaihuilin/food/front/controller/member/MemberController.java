package com.zhaihuilin.food.front.controller.member;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.entity.role.Role;
import com.zhaihuilin.food.code.utils.EncryptionUtil;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.front.config.JedisUtils;
import com.zhaihuilin.food.front.utils.CheckUserUtil;
import com.zhaihuilin.food.front.utils.SecurityUtil;
import com.zhaihuilin.food.service.member.MemberService;
import com.zhaihuilin.food.service.member.RoleService;
import com.zhaihuilin.food.service.member.WeiSellerService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaihuilin on 2018/12/26 16:40.
 */
@RestController
@RequestMapping(value = "/api/member")
@Log4j
public class MemberController {

     @Autowired
     private MemberService memberService;
     @Autowired
     private WeiSellerService weiSellerService;

     @Autowired
     private JedisUtils jedisUtils;

     @Autowired
     private RoleService roleService;

     @Autowired
     private CheckUserUtil checkUserUtil;


  /**
   * 注册  【新用户新增的时候 默认为普通用户】
   * @param phone     手机号
   * @param password  密码
   * @param request
   * @return
   */
     @RequestMapping(value = "/register")
     public ReturnMessages registerMember(
             @RequestParam(name = "phone",required = true) String phone,
             @RequestParam(name = "code",required = true) String code,
             @RequestParam(name = "password",required = true) String password,
             HttpServletRequest request
     ){
       Member member ;
       ReturnMessages returnMessages= null;
        if (memberService.exisMemberByPhone(phone)){
            returnMessages= new ReturnMessages(RequestState.ERROR,"手机号已使用,请更换一个试试",null);
            return  returnMessages;
        }else {
          if (phone.length()!=11){
            returnMessages= new ReturnMessages(RequestState.ERROR,"手机号输入有误",null);
            return  returnMessages;
          }
          // 获取redis存储的code 值
          String redis_code = jedisUtils.get(phone);
          if (!StringUtils.isNotEmpty(redis_code)){
            returnMessages= new ReturnMessages(RequestState.ERROR,"验证码已过期",null);
            return  returnMessages;
          }
          if (!StringUtils.isNotEmpty(code)){
            returnMessages= new ReturnMessages(RequestState.ERROR,"验证码不能为空",null);
            return  returnMessages;
          }
          if (!redis_code.equals(code)){
            returnMessages= new ReturnMessages(RequestState.ERROR,"验证码输入有误",null);
            return  returnMessages;
          }
          if (password.length()<6){
            returnMessages= new ReturnMessages(RequestState.ERROR,"输入的密码格式有误",null);
            return  returnMessages;
          }
          member = new Member( EncryptionUtil.base64Encrypt(password),phone,0);
          // 获取默认的角色
          Role role = roleService.getRoleByDefaule();
          List<Role> roles = new ArrayList<Role>();
          roles.add(role);
          if (member!=null){
             memberService.saveMember(member);
             roleService.setMemberRoleByPhone(member.getPhone(),roles);
             returnMessages = new ReturnMessages(RequestState.SUCCESS,"注册成功！",member);
             return  returnMessages;
          }else {
            returnMessages = new ReturnMessages(RequestState.ERROR,"注册失败！",null);
            return  returnMessages;
          }
        }
     }


  /**
   * 获取自己的相关信息
   * @param request
   * @return
   */
  @PostMapping(value = "/findMe")
  public ReturnMessages findMeMemberInfo(
          HttpServletRequest request
  ){
    ReturnMessages returnMessages =null;
    String username = SecurityUtil.getUsername();
    Member member =null;
    if(username != null){
      member=memberService.findMemberByPhone(username);
    }else {
        returnMessages= new ReturnMessages(RequestState.ERROR,"未登录。",null);
        return returnMessages;
    }

    if(member != null){
      returnMessages= new ReturnMessages(RequestState.SUCCESS,"获取成功。",member);
    }else{
      returnMessages= new ReturnMessages(RequestState.SUCCESS,"未完善资料。",username);
    }
      return  returnMessages;
  }


  /**
   * 编辑自己的相关信息
   * @param username   用户名  【备注： 用户名一旦填写 就没法修改了 】
   * @param nickName   昵称
   * @param name      真实姓名 【备注： 真实姓名一旦填写 就没法修改了】
   * @param email     邮箱
   * @return
   */
  @PostMapping(value = "/updateMe")
  public ReturnMessages updataMemberInfo(
          @RequestParam(name = "memberId",required = true) String memberId,
          @RequestParam(name = "username",required = false) String username,
          @RequestParam(name = "nickName",required = false) String nickName,
          @RequestParam(name = "name",required = false) String name,
          @RequestParam(name = "email",required = false) String email
  ){
    ReturnMessages returnMessages = null;

    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }

    if (!StringUtils.isNotEmpty(memberId)){
       returnMessages = new ReturnMessages(RequestState.ERROR,"用户编号不能为空！",null);
       return  returnMessages;
    }
    Member member =memberService.findMemberByMemberId(memberId);
    if (member ==null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"用户不存在！",null);
       return  returnMessages;
    }
    if (StringUtils.isNotEmpty(nickName)){
      member.setNickName(nickName);
    }
    if (StringUtils.isNotEmpty(username)){
      member.setUsername(username);
    }
    if (StringUtils.isNotEmpty(name)){
      member.setName(name);
    }
    if (StringUtils.isNotEmpty(email)){
      member.setEMail(email);
    }
    member.setUpdateTime(new Date().getTime());
    member = memberService.updateMember(member);
    if (member == null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编辑失败！",null);
      return  returnMessages;
    }else {
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"编辑成功！",member);
      return  returnMessages;
    }
  }


  /**
   * 验证手机号和验证码  【思路： 前端填写旧手机----> 获取验证码 -----> 输入验证码 ----> 是否匹配
   * -----是 ： 跳转到新的(更换手机号)页面 ----->前端填写旧手机----> 获取验证码 -----> 输入验证码 ----> 是否匹配
   * -----是 : 就报存新的手机号】
   * @param phone  手机号
   * @return
   */
  @PostMapping(value = "/CheckPhoneAndCode")
  public ReturnMessages CheckPhoneAndCode(
          @RequestParam(name = "phone",required = true) String phone,
          @RequestParam(name = "code",required = true) String code
  ) {
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(phone)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"手机号不能不填写！",null);
      return  returnMessages;
    }
    Member member =memberService.findMemberByPhone(phone);
    if (member == null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"编辑失败,该手机号不存在！",null);
       return  returnMessages;
    }
    // 获取redis存储的code 值
    returnMessages=getReturnMessages(phone,code);

    return returnMessages;
  }


  /**
   * 更换手机号
   * 【思路： 前端填写旧手机----> 获取验证码 -----> 输入验证码 ----> 是否匹配
   * -----是 ： 跳转到新的(更换手机号)页面 ----->前端填写旧手机----> 获取验证码 -----> 输入验证码 ----> 是否匹配
   * -----是 : 就报存新的手机号】
   * @param memberId  用户编号
   * @param newphone  更换的新手机号
   * @return
   */
  @PostMapping(value = "/updateMePhone")
  public  ReturnMessages updateMePhone(
          @RequestParam(name = "memberId",required = true) String memberId,
          @RequestParam(name = "phone",required = true) String newphone,
          @RequestParam(name = "code",required = true) String code
  ){
    ReturnMessages returnMessages =null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
       return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(memberId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"用户编号不能为空！",null);
      return  returnMessages;
    }
    Member member =memberService.findMemberByMemberId(memberId);
    if (member ==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"用户不存在！",null);
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(newphone)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"新填入的手机号不能为空！",null);
      return  returnMessages;
    }
    if (newphone.equals(member.getPhone())){
      returnMessages = new ReturnMessages(RequestState.ERROR,"新填入的手机号不能和原手机好一致！",null);
      return  returnMessages;
    }
    // 获取redis存储的code 值
    returnMessages=getReturnMessages(newphone,code);
    if (returnMessages!=null){
      return  returnMessages;
    }
    member.setPhone(newphone);
    // 修改用户手机号
    member =memberService.updateMember(member);
    if (member == null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"修改手机号失败！",null);
      return  returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"修改手机号成功！",member);
      return  returnMessages;
    }

  }


  /**
   * 封装的方法  用来判断 验证码的 有效性
   * @param phone  手机号
   * @param code   验证码
   * @return
   */
  public  ReturnMessages getReturnMessages(String phone,String code){
    ReturnMessages returnMessages = null;
    // 获取redis存储的code 值
    String redis_code = jedisUtils.get(phone);
    if (!StringUtils.isNotEmpty(redis_code)){
      returnMessages= new ReturnMessages(RequestState.ERROR,"验证码已过期",null);
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(code)){
      returnMessages= new ReturnMessages(RequestState.ERROR,"验证码不能为空",null);
      return  returnMessages;
    }
    if (!redis_code.equals(code)){
      returnMessages= new ReturnMessages(RequestState.ERROR,"验证码输入有误",null);
      return  returnMessages;
    }
      returnMessages= new ReturnMessages(RequestState.SUCCESS,"验证通过",null);
    return  returnMessages;
  }
}
