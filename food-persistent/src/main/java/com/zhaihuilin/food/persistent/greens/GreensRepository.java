package com.zhaihuilin.food.persistent.greens;

import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜谱持久层
 * Created by zhaihuilin on 2019/1/3 16:24.
 */
@Repository
public interface GreensRepository extends JpaRepository<Greens,String>,JpaSpecificationExecutor<Greens> {

    // 根据菜谱编号进行查询
    public  Greens  findByGreensIdAndDelFalse(String greenId);

    //查询菜谱分类关联的菜谱
    public List<Greens> findByGreensClassAndDelFalse(GreensClass greensClass);

    // 查询用户关联的菜谱
    public List<Greens> findByMemberAndDelFalse(Member member);


    public List<Greens> findByMemberAndDelFalseAndState(Member member,String state);

    //逻辑删除菜谱
    @Query(value = "update Greens g set g.del = true where g.greensId=?1")
    @Modifying
    public void deleteByGreensId(String greensId);

}
