package com.zhaihuilin.food.code.entity.greens;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Lob;
import java.io.Serializable;

/**
 * Created by zhaihuilin on 2019/1/7 17:16.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGreens implements Serializable {

  private  String greensId;

  //菜谱名称
  @NonNull
  private  String greensName;

  //菜谱的封面
  @JsonBackReference
  @Lob
  private String pics;
}
