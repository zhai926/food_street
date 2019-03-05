package com.zhaihuilin.food.code.utils;

import java.util.Random;

/**
 * 随机生成短信验证码工具
 * Created by zhaihuilin on 2018/12/28 13:10.
 */
public class GenerateSmsUtils {

  public static String generateSms() {
    Random rm = new Random();
    String sms = String.valueOf(rm.nextInt(999999));
    int cha = 0;
    while((cha = 6 - sms.length()) > 0) {
      switch (cha) {
        case 1:
          sms = sms.concat(String.valueOf(rm.nextInt(9)));
          break;
        case 2:
          sms = sms.concat(String.valueOf(rm.nextInt(99)));
          break;
        case 3:
          sms = sms.concat(String.valueOf(rm.nextInt(999)));
          break;
        case 4:
          sms = sms.concat(String.valueOf(rm.nextInt(9999)));
          break;
        default:
          sms = sms.concat(String.valueOf(rm.nextInt(99999)));
          break;
      }
    }
    return sms;
  }
}
