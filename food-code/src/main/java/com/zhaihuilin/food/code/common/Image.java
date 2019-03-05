package com.zhaihuilin.food.code.common;

import lombok.*;

import java.io.Serializable;

/**
 * 图片
 * Created by zhaihuilin on 2019/1/3 14:41.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {

  /**
   * 标题
   */
  @NonNull
  private String title;
  /**
   * 地址
   */
  @NonNull
  private String path;
  /**
   * 链接
   */
  @NonNull
  private String url;
}
