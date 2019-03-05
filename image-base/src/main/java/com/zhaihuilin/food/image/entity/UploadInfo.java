package com.zhaihuilin.food.image.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 上传信息
 * Created by zhaihuilin on 2019/1/4 15:57.
 */
@Data
@ToString
public class UploadInfo {
  /**
   * 链接地址
   */
  private String url;

  /**
   * 后缀名
   */
  private String suffix;

  /**
   * 大小
   */
  private long size;
}
