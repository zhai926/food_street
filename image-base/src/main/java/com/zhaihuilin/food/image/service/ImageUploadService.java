package com.zhaihuilin.food.image.service;

import com.zhaihuilin.food.image.entity.UploadInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhaihuilin on 2019/1/4 15:57.
 */
public interface ImageUploadService {

  /**
   * 上传单个
   * @return
   */
  public UploadInfo upload(MultipartFile multipartFile);

}
