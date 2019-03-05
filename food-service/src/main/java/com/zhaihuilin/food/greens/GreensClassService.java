package com.zhaihuilin.food.greens;

import com.zhaihuilin.food.code.entity.greens.GreensClass;

import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/3 16:45.
 */
public interface GreensClassService {

  //根据分类编号进行查询
  public GreensClass findByGreensClassIdAndDelFalse(String greensClassId);

  // 保存菜谱分类消息
  public  GreensClass  saveGreensClass(GreensClass greensClass);

  // 编辑菜谱分类消息
  public  GreensClass  updateGreensClass(GreensClass greensClass);

  //判断该分类是否存在
  public  boolean existGreensClass(String greensClassId);

  //判断该分类是否存在
  public  boolean existGreensClassByName(String greensClassName);

  //根据分类名称进行查询
  public  GreensClass  findByGreensClassNameAndDelFalse(String greensClassName);

  //物理删除
  public  boolean physicallyDeleteByGreensClassId(String greensClassId);

  //逻辑删除
  public  boolean deleteByClassId(String classId);

  //获取所有的二级分类
  public List<GreensClass> findByOldClassIsNotNullAndDelFalse();

  //获取所有的一级分类
  public List<GreensClass>  findByOldClassIsNullAndDelFalse();

  //根据父类获取其下的子类
  public  List<GreensClass> findGreensClassByOldClassAndDelFalse(GreensClass oldClass);


}
