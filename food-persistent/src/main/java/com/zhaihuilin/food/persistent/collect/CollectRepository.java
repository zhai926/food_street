package com.zhaihuilin.food.persistent.collect;

import com.zhaihuilin.food.code.entity.collect.Collect;
import com.zhaihuilin.food.code.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 我的收藏
 * Created by zhaihuilin on 2019/1/8 14:45.
 */
@ResponseBody
public interface CollectRepository extends JpaRepository<Collect ,String> {

    // 根据编号进行查询
    public  Collect  findByCollectId(String collectId);

    //根据用户获取自己的收藏
    public List<Collect> findAllByMember(Member member);

}
