package com.zhaihuilin.food.hotactivity;

import com.zhaihuilin.food.code.entity.hotactivity.HotActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/8 13:07.
 */
public interface HotActivityService {

  // 根据人们活动编号进行查询
  public HotActivity findByHotActIdAndDelFalse(String hotActId );

  //根据热门活动名称进行查询
  public  HotActivity findByHotActivitytitleTrue(String hotActivitytitle);

  //根据条件查询 分页
  public Page<HotActivity> findAll(HotActivity hotActivity, Pageable pageable);

  //分页条件查询
  public Page<HotActivity> findAll(HotActivity hotActivity,  Long startDate,Long endDate,Pageable pageable);

  //根据条件查询 不分页
  public List<HotActivity> findList(HotActivity hotActivity);

  //新增热门活动信息
  public HotActivity saveHotActivity(HotActivity hotActivity);

  //保存热门活动信息
  public HotActivity updateHotActivity(HotActivity hotActivity);

  //逻辑删除
  public boolean deleteById(String hotActId );

  // 物理删除
  public boolean physicallyDeleteByHotActId(String hotActId);
}
