package com.zhaihuilin.food.image.controller;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.image.entity.UploadInfo;
import com.zhaihuilin.food.image.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhaihuilin on 2019/1/4 15:56.
 */
@RestController
@RequestMapping(value = "/api")
public class UploadController{

  @Autowired
  ImageUploadService uploadService;

  @PostMapping(value = "/upload")
  public ReturnMessages upload(MultipartFile file){
    UploadInfo uploadInfo = uploadService.upload(file);
    if(uploadInfo != null){
      return new ReturnMessages(RequestState.SUCCESS,"上传成功。",uploadInfo);
    }
      return new ReturnMessages(RequestState.SUCCESS,"上传失败。",null);
  }

}
