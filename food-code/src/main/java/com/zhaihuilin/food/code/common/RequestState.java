package com.zhaihuilin.food.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态
 * Created by zhaihuilin on 2018/12/26 15:33.
 */
@AllArgsConstructor
@Getter
public enum RequestState {

  /**
   * 访问出错
   */
  ERROR(400),
  /**
   * 访问成功
   */
  SUCCESS(200),
  /**
   * 权限异常
   */
  AUTHENTICATION_ERROR(403);

  /**
   * 状态码
   */
  private final int stateCode;


}
