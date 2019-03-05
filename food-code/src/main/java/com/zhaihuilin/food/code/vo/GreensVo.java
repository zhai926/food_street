package com.zhaihuilin.food.code.vo;

import com.zhaihuilin.food.code.entity.greens.SimpleGreens;
import com.zhaihuilin.food.code.entity.greens.SimpleGreensClass;
import com.zhaihuilin.food.code.entity.member.SimplMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhaihuilin on 2019/1/7 17:08.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GreensVo implements Serializable {

   //菜谱编号
   private  String greensId;

   private SimpleGreens greens;

   private SimpleGreensClass greensClass;

   private SimplMember member;

}
