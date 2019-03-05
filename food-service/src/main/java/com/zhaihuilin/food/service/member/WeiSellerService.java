package com.zhaihuilin.food.service.member;

import com.zhaihuilin.food.code.entity.member.WeiSeller;

/**
 * Created by zhaihuilin on 2018/12/26 16:20.
 */
public interface WeiSellerService {

  // 根据OpenId 进行查询
  public WeiSeller findWeiSellerByOpenId(String openId);

  //根据用户编号进行查询
  public  WeiSeller findWeiSellerByMemberId(String memberId);

  //保存用户信息
  public  WeiSeller saveWeiSeller(WeiSeller weiSeller);

  //编辑用户信息
  public  WeiSeller updateWeiSeller(WeiSeller weiSeller);
}
