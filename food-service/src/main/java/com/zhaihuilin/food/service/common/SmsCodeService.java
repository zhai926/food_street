package com.zhaihuilin.food.service.common;

import com.zhaihuilin.food.code.entity.common.SmsCode;

/**
 * 短信接口服务
 * Created by zhaihuilin on 2018/12/28 13:45.
 */
public interface SmsCodeService {

  public SmsCode findSmsCodeByMtMsgId(String mtMsgId);

  public  SmsCode findSmsCodeByPhoneAndRequestTime(String phone,long requestTime);

  public  SmsCode updateSmsCodeByMtMsgId(SmsCode smsCode);

  public  SmsCode saveSmsCode(SmsCode smsCode);
}
