package com.zhaihuilin.food.service.common.impl;

import com.zhaihuilin.food.code.entity.common.SmsCode;
import com.zhaihuilin.food.persistent.common.SmsCodeRepository;
import com.zhaihuilin.food.service.common.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaihuilin on 2018/12/28 13:46.
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

  @Autowired
  private SmsCodeRepository smsCodeRepository;

  @Override
  public SmsCode findSmsCodeByMtMsgId(String mtMsgId) {
    return smsCodeRepository.findSmsCodeByMtMsgId(mtMsgId);
  }

  @Override
  public SmsCode findSmsCodeByPhoneAndRequestTime(String phone, long requestTime) {
    return smsCodeRepository.findSmsCodeByPhoneAndRequestTime(phone,requestTime);
  }

  @Override
  public SmsCode updateSmsCodeByMtMsgId(SmsCode smsCode) {
    return smsCodeRepository.updateSmsCodeByMtMsgId(smsCode);
  }


  @Override
  public SmsCode saveSmsCode(SmsCode smsCode) {
    return smsCodeRepository.save(smsCode);
  }


}
