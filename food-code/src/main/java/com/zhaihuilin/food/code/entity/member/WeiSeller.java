package com.zhaihuilin.food.code.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by zhaihuilin on 2018/12/26 15:45.
 */
@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name="food_wei_member")
@AllArgsConstructor
public class WeiSeller  implements Serializable{

  @Id
  @GeneratedValue
  private long id;
  //公众号提供的用户id
  private String openId;
  // 用户编号
  private String  memberId;
  //微信用户身份的唯一标识
  private String unionId;
  //用户的审核状态（0未审核  1已审核）
  private Integer checkState;
  //用户微信昵称
  private String nickName;
  //用户真实姓名
  private String trueName;
  //用户注册时间
  private long createTime;
  //用户修改时间
  private long updateTime;

}
