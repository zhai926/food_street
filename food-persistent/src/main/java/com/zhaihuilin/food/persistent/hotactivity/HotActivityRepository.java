package com.zhaihuilin.food.persistent.hotactivity;

import com.zhaihuilin.food.code.entity.hotactivity.HotActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 热门活动持久层
 * Created by zhaihuilin on 2019/1/8 11:47.
 */
@Repository
public interface HotActivityRepository extends JpaRepository<HotActivity,String>,JpaSpecificationExecutor<HotActivity>{

    // 根据人们活动编号进行查询
    public  HotActivity findByHotActIdAndDelFalse(String hotActId );

    //根据热门活动名称进行查询
    public  HotActivity findByHotActivitytitleTrue(String hotActivitytitle);

    //逻辑删除
    @Query("update HotActivity ht set  ht.del = true where ht.hotActId=?1")
    @Modifying
    public void  deleteHotActivityByHotActId(String hotActId);
}
