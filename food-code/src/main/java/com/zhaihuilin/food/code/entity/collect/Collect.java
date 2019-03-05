package com.zhaihuilin.food.code.entity.collect;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.vo.GreensVo;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的收藏
 * Created by zhaihuilin on 2019/1/7 17:02.
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_collect")
public class Collect implements Serializable {

  @Id
  @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
          @Parameter(name = "k",value = "C")
  })
  @GeneratedValue(generator = "sys-uid")
  private  String  collectId;

  //创建时间
  private long createTime =new Date().getTime();

  //编辑时间
  private long updateTime;

  //是否删除
  private boolean del = Boolean.FALSE;

  /**
   * 所属用户
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ManyToOne(cascade = {},fetch =FetchType.EAGER)
  @JoinColumn(name = "member_id")
  private Member member;

  //菜谱映射实体类
  @Transient     //数据库不存在这个字段的 时候  用 @Transient
  @NonNull
  private List<GreensVo> greensVoList;

  //菜谱存储实体类
  @JsonBackReference
  @Lob
  private String  greensVos;


  // 获取的时候
  @PostLoad
  private void load(){
    if(greensVos != null){
      Gson gson = new Gson();
      Type type = new TypeToken<ArrayList<GreensVo>>() {}.getType();
      greensVoList = gson.fromJson(greensVos,type);
      greensVos = null;
    }
  }

  // 保存的时候
  @PrePersist
  @PreUpdate
  private void save(){
    // 食材封面
    if(greensVoList != null){
      Gson gson = new Gson();
      greensVos = gson.toJson(greensVoList);
    }
  }



}
