package com.zhaihuilin.food.persistent.member;

import com.zhaihuilin.food.code.entity.member.WeiSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaihuilin on 2018/12/26 16:05.
 */
@Repository
public interface WeiSellweRepository extends JpaRepository<WeiSeller,Long> {

   // 根据OpenId 进行查询
   public  WeiSeller findWeiSellerByOpenId(String openId);

   //根据用户编号进行查询
   public  WeiSeller findWeiSellerByMemberId(String memberId);
}
