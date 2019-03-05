package com.zhaihuilin.food.greens;

import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/3 16:35.
 */
public interface GreensService {

  // 根据菜谱编号进行查询
  public Greens findByGreensIdAndDelFalse(String greenId);

  //查询菜谱分类关联的菜谱
  public List<Greens> findByGreensClassAndDelFalse(GreensClass greensClass);

  // 查询用户关联的菜谱
  public List<Greens> findByMemberAndDelFalse(Member member);

  // 查询用户关联的菜谱以及状态
  public List<Greens> findByMemberAndDelFalseAndState(Member member,String state);


  // 保存菜谱消息
  public  Greens saveGreens(Greens greens);

  // 编辑菜谱消息
  public  Greens updateGreens(Greens greens);

  //根据条件分页查询
  public Page<Greens> findAll(Greens greens, Pageable pageable);

  /**
   * 根据条件分页查询
   * @param greens
   * @param member
   * @param greensClass
   * @param pageable
   * @return
   */
  public Page<Greens> findGreens(Greens greens,Member member,GreensClass greensClass,Pageable pageable);

  //根据条件查询 不分页
  public List<Greens> findAll(Greens greens);

  //逻辑删除
  public boolean deleteGreensByGreenId(String greensId);

  //物理删除
  public boolean physicallyDeleteByGreenId(String greensId);

  //判断该菜谱是否存在
  public  boolean  existGreens(String greensId);




}
