package com.zhaihuilin.food.code.entity.greens;

import lombok.*;

import java.io.Serializable;

/**
 * Created by zhaihuilin on 2019/1/7 17:21.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGreensClass implements Serializable {

  private  String greensClassId;

  //菜谱分类名称
  private  String greensClassName;


  public SimpleGreensClass(GreensClass greensClass) {
    this.greensClassId = greensClass.getGreensClassId();
    this.greensClassName = greensClass.getGreensClassName();
  }

}
