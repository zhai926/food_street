package com.zhaihuilin.food.code.common;

import lombok.*;

import java.io.Serializable;

/**
 * 食物的做法步骤
 * Created by zhaihuilin on 2019/1/3 15:43.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FoodStep implements Serializable {

     //第几步骤
     @NonNull
     private  int  stepNum;

     //步骤说明
     @NonNull
     private  String  stepState;

    //菜谱的封面
    @NonNull
    private String url;
}
