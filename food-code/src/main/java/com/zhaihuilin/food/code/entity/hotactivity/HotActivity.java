package com.zhaihuilin.food.code.entity.hotactivity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhaihuilin.food.code.common.Image;
import com.zhaihuilin.food.code.constant.StateConstant;
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
 * Created by zhaihuilin on 2019/1/7 16:48.
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_hotactivity")
public class HotActivity implements Serializable {

     @Id
     @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
          @Parameter(name = "k",value = "HA")
     })
     @GeneratedValue(generator = "sys-uid")
     private String  hotActId;

     // 活动标题
     @NonNull
     private String hotActivitytitle;

    //创建时间
    private long createTime =new Date().getTime();

    //编辑时间
    private long updateTime;

    //是否删除
    private boolean del = Boolean.FALSE;

    //活动状态
    private String  state = StateConstant.HOT_ACT_STATE_ON_WAY.toString() ;

    //活动的封面
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Image> greensPic;
    @JsonBackReference
    @Lob
    private String pics;

    // 活动内容
    @NonNull
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  StringBuffer   content;


  // 获取的时候
  @PostLoad
  private void load(){
    if(pics != null){
      Gson gson = new Gson();
      Type type = new TypeToken<ArrayList<Image>>() {}.getType();
      greensPic = gson.fromJson(pics,type);
      pics = null;
    }
  }

  // 保存的时候
  @PrePersist
  @PreUpdate
  private void save(){
    // 食材封面
    if(greensPic != null){
      Gson gson = new Gson();
      pics = gson.toJson(greensPic);
    }
  }
}
