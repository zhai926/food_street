package com.zhaihuilin.food.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhaihuilin on 2019/1/4 16:00.
 */
@Component
public class UploadConfig {
  /**
   * 上传地址
   */
  @Value(value = "${upload.filepath}")
  public String FILE_PATH;

  /**
   * 链接地址
   */
  @Value(value = "${upload.baseurl}")
  public String BASE_URL;
}
