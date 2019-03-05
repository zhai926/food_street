package com.zhaihuilin.food.code.entity.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaihuilin on 2019/2/27 10:46.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_pp_video")
public class Video implements Serializable {

  //视频Id
  @Id
  @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
          @Parameter(name = "k",value = "V")
  })
  @GeneratedValue(generator = "sys-uid")
  private String videoId;

  // 视频名称
  private String videoName;

  //视频存储地址
  private String videoPath;

  //排序
  private Integer orderBy;

  //视频连接
  private String videoUrl;

  //是否删除
  private boolean isdel;

  //创建时间
  private Date createTime;

  //修改时间
  private Date updateTime;

}
