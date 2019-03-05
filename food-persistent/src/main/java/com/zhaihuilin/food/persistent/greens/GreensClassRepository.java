package com.zhaihuilin.food.persistent.greens;

import com.zhaihuilin.food.code.entity.greens.GreensClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜谱分类持久层
 * Created by zhaihuilin on 2019/1/3 16:05.
 */
@Repository
public interface GreensClassRepository extends JpaRepository<GreensClass,String>,JpaSpecificationExecutor<GreensClass> {

    //根据分类编号进行查询
    public  GreensClass  findByGreensClassIdAndDelFalse(String greensClassId);

   //逻辑删除分类
   @Query(value = "update GreensClass gc set gc.del = true where gc.greensClassId = ?1")
   @Modifying
   public void deleteByClassId(String classId);


   //根据分类名称进行查询
   public GreensClass  findByGreensClassNameAndDelFalse(String greensClassName);


   //获取所有的二级分类
   public  List<GreensClass> findByOldClassIsNotNullAndDelFalse();

   //获取所有的一级分类
   public List<GreensClass>  findByOldClassIsNullAndDelFalse();

   //根据父类获取其下的子类
   public  List<GreensClass> findByOldClassAndDelFalse(GreensClass oldClass);

}
