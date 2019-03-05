package com.zhaihuilin.food.code.common;

import lombok.*;

import java.io.Serializable;

/**
 * 食材
 * Created by zhaihuilin on 2019/1/3 14:47.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FoodMaterial implements Serializable {

    // 食材名称
    @NonNull
    private  String   foodMaterialName;

    // 用量
    @NonNull
    private  String   dosage;
}
