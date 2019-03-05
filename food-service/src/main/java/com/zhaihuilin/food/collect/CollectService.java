package com.zhaihuilin.food.collect;

import com.zhaihuilin.food.code.entity.collect.Collect;
import com.zhaihuilin.food.code.entity.member.Member;

import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/8 14:49.
 */
public interface CollectService {

  // 根据编号进行查询
  public Collect findByCollectId(String collectId);

  //新增
  public Collect saveCollect(Collect collect);

  //编辑
  public Collect updateCollect(Collect collect);

  //根据用户获取自己的收藏
  public List<Collect> findAllByMember(Member member);

  //删除
  public  boolean deleteById(String collectId);

  //是否收藏某菜谱
  public  boolean existByGreensId(String greensId,String memberId);
}