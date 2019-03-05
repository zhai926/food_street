package com.zhaihuilin.food.persistent.common;

import com.zhaihuilin.food.code.entity.common.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *  手机短信
 * Created by zhaihuilin on 2018/12/28 13:41.
 */
@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCode,Integer> {

  //根据短信平台反馈的短信唯一标识进行查询
  public  SmsCode findSmsCodeByMtMsgId(String mtMsgId);

  //根据手机号和请求时间进行查询
  public  SmsCode findSmsCodeByPhoneAndRequestTime(String phone,long requestTime);

  //根据短信平台反馈的短信唯一标识进行修改
  @Query(value = " update SmsCode s set s.updateTime=?2,s.state=?3,s.requestTime=?4,s.smsCode=?5,s.errCode=?6 where s.mtMsgId=?1")
  public  SmsCode updateSmsCodeByMtMsgId(SmsCode smsCode);


}
