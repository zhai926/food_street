package com.zhaihuilin.food.service.member.impl;

import com.zhaihuilin.food.code.entity.member.WeiSeller;
import com.zhaihuilin.food.persistent.member.WeiSellweRepository;
import com.zhaihuilin.food.service.member.WeiSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaihuilin on 2018/12/26 16:21.
 */
@Service
public class WeiSellerServiceImpl implements WeiSellerService {

  @Autowired
  private WeiSellweRepository weiSellweRepository;

  @Override
  public WeiSeller findWeiSellerByOpenId(String openId) {
    return weiSellweRepository.findWeiSellerByOpenId(openId);
  }

  @Override
  public WeiSeller findWeiSellerByMemberId(String memberId) {
    return weiSellweRepository.findWeiSellerByMemberId(memberId);
  }

  @Override
  @Transactional
  public WeiSeller saveWeiSeller(WeiSeller weiSeller) {
    return weiSellweRepository.save(weiSeller);
  }

  @Override
  @Transactional
  public WeiSeller updateWeiSeller(WeiSeller weiSeller) {
    return weiSellweRepository.save(weiSeller);
  }
}
