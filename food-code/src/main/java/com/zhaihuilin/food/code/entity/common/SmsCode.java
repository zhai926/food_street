package com.zhaihuilin.food.code.entity.common;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * Created by zhaihuilin on 2018/12/28 13:36.
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "food_smscode")
@AllArgsConstructor
public class SmsCode implements Serializable {

  @Id
  @GeneratedValue(generator = "")
  private Integer smsId;
  private String mtMsgId;             //短信平台反馈的短信唯一标识
  private String sa;                  //源手机号
  @NonNull
  private String phone;               //买家手机号
  @NonNull
  private String smsCode;              //短信验证码
  private long requestTime = new Date().getTime();            //请求短信时间
  private String state;               //其值为ACCEPTD表示MLINK端成功接收到SP端的请求，其他值时表示MLINK端接收SP端请求失败。
  private String errCode;              //其值为000，表示无错误返回，其他值时表示MLINK端接收SP端请求失败
  private Timestamp updateTime;        //短信状态反馈更新时间

  public SmsCode(String mtMsgId, String sa, String phone, String smsCode, String state, String errCode) {
    this.mtMsgId = mtMsgId;
    this.sa = sa;
    this.phone = phone;
    this.smsCode = smsCode;
    this.state = state;
    this.errCode = errCode;
  }
}
