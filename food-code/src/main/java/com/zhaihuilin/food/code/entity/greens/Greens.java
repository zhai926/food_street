package com.zhaihuilin.food.code.entity.greens;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhaihuilin.food.code.common.FoodMaterial;
import com.zhaihuilin.food.code.common.FoodStep;
import com.zhaihuilin.food.code.common.Image;
import com.zhaihuilin.food.code.constant.StateConstant;
import com.zhaihuilin.food.code.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * 菜谱 实体类
 * Created by zhaihuilin on 2019/1/3 14:36.
 */
@Data
@Entity
@ToString
@Table(name="food_greens")
@NoArgsConstructor
@AllArgsConstructor
public class Greens implements Serializable {

    @Id
    @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
          @Parameter(name = "k",value = "G")
    })
    @GeneratedValue(generator = "sys-uid")
    private  String greensId;

    //菜谱名称
    @NonNull
    private  String greensName;

    //菜谱简介
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  StringBuffer greensIntro;

    //创建时间
    private long createTime =new Date().getTime();

    //编辑时间
    private long updateTime;

    //是否删除
    private boolean del = Boolean.FALSE;

    // 菜谱状态
    private String  state = StateConstant.GREENS_STATE_ON_CHECKING.toString();


    //温馨小提示
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StringBuffer greensBody;


    //所需食材
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FoodMaterial> foodMaterialList;
    @JsonBackReference
    @Lob
    private  String foodMaterials;

    //菜谱的封面
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Image> greensPic;
    @JsonBackReference
    @Lob
    private String pics;

    //菜谱的做法步骤
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FoodStep> foodStepList;
    @JsonBackReference
    @Lob
    private  String foodSteps;


    //所属用户
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    //所属菜谱分类
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "greensClass_id")
    private GreensClass greensClass;



    // 获取的时候
    @PostLoad
    private void load(){
      if(pics != null){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Image>>() {}.getType();
        greensPic = gson.fromJson(pics,type);
        pics = null;
      }

      if(foodMaterials != null){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FoodMaterial>>() {}.getType();
        foodMaterialList = gson.fromJson(foodMaterials,type);
        foodMaterials = null;
      }

      if (foodSteps!=null){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FoodStep>>() {}.getType();
        foodStepList = gson.fromJson(foodSteps,type);
        foodSteps = null;
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
      //食材
      if(foodMaterialList != null){
        Gson gson = new Gson();
        foodMaterials = gson.toJson(foodMaterialList);
      }
      //步骤
      if (foodStepList!=null){
         Gson gson = new Gson();
         foodSteps = gson.toJson(foodStepList);
      }
     }

}
