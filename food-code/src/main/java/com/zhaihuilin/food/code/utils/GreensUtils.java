package com.zhaihuilin.food.code.utils;

import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.greens.SimpleGreens;
import com.zhaihuilin.food.code.entity.greens.SimpleGreensClass;
import com.zhaihuilin.food.code.entity.member.SimplMember;
import com.zhaihuilin.food.code.vo.GreensVo;

/**
 * 将菜谱实体类映射成简单的实体类
 * Created by zhaihuilin on 2019/1/8 15:33.
 */
public class GreensUtils {

  // 转换
  public static GreensVo toGoodsVo(Greens greens){
    if(greens != null){
        GreensVo greensVo = new GreensVo();
        //
        SimpleGreens simpleGreens = new SimpleGreens();
        simpleGreens.setGreensId(greens.getGreensId());
        simpleGreens.setGreensName(greens.getGreensName());
        simpleGreens.setPics(greens.getPics());
        //
        SimplMember simplMember = new SimplMember();
        simplMember.setMemberId(greens.getMember().getMemberId());
        simplMember.setUsername(greens.getMember().getUsername());
        //
        SimpleGreensClass simpleGreensClass = new SimpleGreensClass();
        simpleGreensClass.setGreensClassId(greens.getGreensClass().getGreensClassId());
        simpleGreensClass.setGreensClassName(greens.getGreensClass().getGreensClassName());
        /******************************/
        greensVo.setGreens(simpleGreens);
        greensVo.setGreensClass(simpleGreensClass);
        greensVo.setMember(simplMember);
        greensVo.setGreensId(greens.getGreensId());
        return greensVo;
    }
    return null;
  }
}
