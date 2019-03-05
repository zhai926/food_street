package com.zhaihuilin.food.admin.utils;

import com.zhaihuilin.food.code.entity.member.Member;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 获取用户名
 * Created by zhaihuilin on 2018/12/28 17:30.
 */
@Log4j
public class SecurityUtil {

  public static String getUsername(){
       Subject subject = SecurityUtils.getSubject();
       if (subject!=null){
          Member member =(Member)subject.getPrincipal();
          log.info("获取的登录的用户名:" + member.getUsername() );
          log.info("获取的登录的用户手机号:" + member.getPhone());
          return  member.getPhone();
       }
       return  null;
  }
}
