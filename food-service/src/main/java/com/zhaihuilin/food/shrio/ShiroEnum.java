package com.zhaihuilin.food.shrio;

/**
 * Created by zhaihuilin on 2018/12/28 17:10.
 */
public enum  ShiroEnum {

  UNAUTHORIZTION(990, "认证失败"),
  UNAUTHENTICATION(991, "用户无权限");

  private Integer code;

  private String desc;

  ShiroEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static ShiroEnum getByCode(Integer code) {
    for (ShiroEnum item : ShiroEnum.values()) {
      if (item.getCode().equals(code)) {
        return item;
      }
    }
    return null;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
