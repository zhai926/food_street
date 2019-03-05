package com.zhaihuilin.food.front.utils;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 判断当前登录用户的状态
 * Created by zhaihuilin on 2018/12/29 17:19.
 */
@Service
@Transactional
public class CheckUserUtil {
  @Autowired
  private MemberService memberService;


  /**
   * 获取当前登录的用户
   * @return  member
   */
  public   Member  getMember(){
    String  userName= SecurityUtil.getUsername();
    Member member=memberService.findMemberByPhone(userName);
    return  member;
  }

  public ReturnMessages returnMessages(){
    ReturnMessages returnMessages = null;
    Member member = getMember();
    if (member == null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"未登录！",null);
       return returnMessages;
    }
    return  returnMessages;
  }
}
