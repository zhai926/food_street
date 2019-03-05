package com.zhaihuilin.food.code.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhaihuilin on 2019/1/7 17:22.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimplMember implements Serializable {

  private  String  memberId;

  /**
   * 用户名
   */
  private String username;


  public SimplMember(Member member) {
    this.memberId = member.getMemberId();
    this.username = member.getUsername();
  }
}
