package com.zhaihuilin.food.collect.impl;

import com.zhaihuilin.food.code.entity.collect.Collect;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.vo.GreensVo;
import com.zhaihuilin.food.collect.CollectService;
import com.zhaihuilin.food.persistent.collect.CollectRepository;
import com.zhaihuilin.food.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/8 14:52.
 */
@Service
public class CollectServiceImpl implements CollectService {

  @Autowired
  private CollectRepository collectRepository;

  @Autowired
  private MemberService memberService;


  @Override
  public Collect findByCollectId(String collectId) {
    return collectRepository.findByCollectId(collectId);
  }

  @Override
  public Collect saveCollect(Collect collect) {
    return collectRepository.save(collect);
  }

  @Override
  public Collect updateCollect(Collect collect) {
    return collectRepository.save(collect);
  }

  //获取自己所有的收藏
  @Override
  public List<Collect> findAllByMember(Member member) {
    return collectRepository.findAllByMember(member);
  }


  @Override
  public boolean deleteById(String collectId) {
    Collect  collect =findByCollectId(collectId);
    if (collect!=null){
       collectRepository.delete(collectId);
       return true;
    }
    return false;
  }

  //判断是否收藏了某菜谱
  @Override
  public boolean existByGreensId(String greensId,String memberId) {
    boolean flag = Boolean.FALSE;
    //先获取当前用户
    Member member=memberService.findMemberByMemberId(memberId);
    //获取自己所有的收藏
    List<Collect>  collectList=findAllByMember(member);
    List<String> greensIds = new ArrayList<>();
    if (collectList!=null && collectList.size()>0){
       for (Collect collect:collectList){
          //获取所有菜谱集合
          List<GreensVo> greensVoList= collect.getGreensVoList();
          for (GreensVo greensVo:greensVoList){
              greensIds.add( greensVo.getGreensId());
          }
       }
    }
    if (greensIds!=null && greensIds.size()>0){
         for (String id: greensIds){
            if (id.equals(greensId)){
              flag= Boolean.TRUE;
            }
         }
    }
    return flag;
  }
}
