package com.zhaihuilin.food.code.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 消息返回类型
 * Created by zhaihuilin on 2018/12/26 15:32.
 */
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReturnMessages implements Serializable {

  /**
   * 返回状态
   */
  private RequestState state;

  /**
   * 返回信息
   */
  private String messages;

  /**
   * 返回对象
   */
  private Object content;

  /**
   * 获取消息状态编码
   * 修改请注意,以免返回状态名称而非状态编码.
   * @return
   */
  public int getState() {
    return state.getStateCode();
  }

  public String getMessages() {
    return messages;
  }

  public Object getContent() {
    return content;
  }
}
