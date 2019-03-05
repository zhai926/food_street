package com.zhaihuilin.food.code.utils;

/**
 * 字符串验证
 * Created by zhaihuilin on 2018/12/26 15:13.
 */
public class StringUtils {

  public static boolean isNotEmpty(String str){
    if(str != null && str.trim().length() != 0){
      return true;
    }
    return false;
  }
}
