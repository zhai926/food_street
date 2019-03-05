package com.zhaihuilin.food.greens.impl;

import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.greens.GreensClassService;
import com.zhaihuilin.food.persistent.greens.GreensClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/3 16:56.
 */
@Service
public class GreensClassServiceImpl implements GreensClassService {

  @Autowired
  private GreensClassRepository greensClassRepository;

  //根据菜谱编号获取信息
  @Override
  public GreensClass findByGreensClassIdAndDelFalse(String greensClassId) {
    return greensClassRepository.findByGreensClassIdAndDelFalse(greensClassId);
  }


  //保存菜谱分类
  @Override
  public GreensClass saveGreensClass(GreensClass greensClass) {
    return greensClassRepository.save(greensClass);
  }

  //编辑菜谱分类
  @Override
  public GreensClass updateGreensClass(GreensClass greensClass) {
    return greensClassRepository.save(greensClass);
  }

  //判断该菜谱是否存在 【 根据编号 】
  @Override
  public boolean existGreensClass(String greensClassId) {
    GreensClass greensClass =  greensClassRepository.findByGreensClassIdAndDelFalse(greensClassId);
    if (greensClass!=null){
       return  true;
    }
    return false;
  }

  //判断该菜谱是否存在 【 根据分类名称 】
  @Override
  public boolean existGreensClassByName(String greensClassName) {
    GreensClass greensClass = greensClassRepository.findByGreensClassNameAndDelFalse(greensClassName);
    if (greensClass!=null){
      return  true;
    }
    return false;
  }

  //根据名称进行查询
  @Override
  public GreensClass findByGreensClassNameAndDelFalse(String greensClassName) {
    return greensClassRepository.findByGreensClassNameAndDelFalse(greensClassName);
  }

  // 物理删除
  @Override
  public boolean physicallyDeleteByGreensClassId(String greensClassId) {
    GreensClass greensClass = greensClassRepository.findByGreensClassIdAndDelFalse(greensClassId);
    if (greensClass!=null){
        greensClassRepository.delete(greensClassId);
        return  true;
    }
    return false;
  }

  // 逻辑删除
  @Override
  public boolean deleteByClassId(String classId) {
    GreensClass greensClass = greensClassRepository.findByGreensClassIdAndDelFalse(classId);
    if (greensClass!=null){
      greensClassRepository.deleteByClassId(classId);
      return  true;
    }
    return false;
  }

  //获取所有的二级分类
  @Override
  public List<GreensClass> findByOldClassIsNotNullAndDelFalse() {
    return greensClassRepository.findByOldClassIsNotNullAndDelFalse();
  }

  //获取所有的一级分类
  @Override
  public List<GreensClass> findByOldClassIsNullAndDelFalse() {
    return greensClassRepository.findByOldClassIsNullAndDelFalse();
  }

  //根据父类获取其下的子类
  @Override
  public List<GreensClass> findGreensClassByOldClassAndDelFalse(GreensClass oldClass) {
    return greensClassRepository.findByOldClassAndDelFalse(oldClass);
  }
}
