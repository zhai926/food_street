package com.zhaihuilin.food.greens.impl;

import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.greens.GreensService;
import com.zhaihuilin.food.persistent.greens.GreensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/3 16:57.
 */
@Service
public class GreensServiceImpl implements GreensService {

  @Autowired
  private GreensRepository greensRepository;



  @Override
  public Greens findByGreensIdAndDelFalse(String greenId) {
    return greensRepository.findByGreensIdAndDelFalse(greenId);
  }

  @Override
  public List<Greens> findByGreensClassAndDelFalse(GreensClass greensClass) {
    return greensRepository.findByGreensClassAndDelFalse(greensClass);
  }


  @Override
  public List<Greens> findByMemberAndDelFalse(Member member) {
    return greensRepository.findByMemberAndDelFalse(member);
  }

  @Override
  public List<Greens> findByMemberAndDelFalseAndState(Member member, String state) {
    return greensRepository.findAll(greensWhere(member,state));
  }

  @Override
  public Greens saveGreens(Greens greens) {
    return greensRepository.save(greens);
  }

  @Override
  public Greens updateGreens(Greens greens) {
    return greensRepository.save(greens);
  }

  @Override
  public Page<Greens> findAll(Greens greens, Pageable pageable) {
    return greensRepository.findAll(greensWhere(greens),pageable);
  }

  @Override
  public Page<Greens> findGreens(Greens greens, Member member, GreensClass greensClass, Pageable pageable) {
    return greensRepository.findAll(greensWhere(greens,member,greensClass),pageable);
  }

  @Override
  public List<Greens> findAll(Greens greens) {
    return greensRepository.findAll(greensWhere(greens));
  }

  @Override
  public boolean deleteGreensByGreenId(String greensId) {
    Greens greens =findByGreensIdAndDelFalse(greensId);
    if (greens!=null){
        greensRepository.deleteByGreensId(greensId);
        return  true;
    }
    return false;
  }

  // 物理删除
  @Override
  public boolean physicallyDeleteByGreenId(String greensId) {
    Greens greens =findByGreensIdAndDelFalse(greensId);
    if (greens!=null){
      greensRepository.delete(greensId);
      return  true;
    }
    return false;
  }

  @Override
  public boolean existGreens(String greensId) {
    Greens greens =findByGreensIdAndDelFalse(greensId);
    if (greens!=null){
       return true;
    }
    return false;
  }


  public  static Specification<Greens> greensWhere(
          final  Greens greens
  ){
     return new Specification<Greens>(){
      @Override
      public Predicate toPredicate(Root<Greens> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (greens.getGreensId()!=null && !greens.getGreensId().equals("")){
           predicates.add(criteriaBuilder.equal(root.<String>get("greensId"),greens.getGreensId()));
        }
        if (greens.getGreensName()!=null && !greens.getGreensName().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("greensName"),greens.getGreensName()));
        }
        if (greens.getState()!=null && !greens.getState().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("state"),greens.getState()));
        }
        predicates.add(criteriaBuilder.equal(root.<Boolean>get("del"),false));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
   }

  public  static Specification<Greens> greensWhere(
          final  Member member,
          final  String state
  ){
    return new Specification<Greens>(){
      @Override
      public Predicate toPredicate(Root<Greens> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.isNotEmpty(state)){
          predicates.add(criteriaBuilder.equal(root.<String>get("state"),state));
        }
        if (member!=null && StringUtils.isNotEmpty(member.getMemberId())){
          predicates.add(criteriaBuilder.equal(root.<String>get("member"),member));
        }
        predicates.add(criteriaBuilder.equal(root.<Boolean>get("del"),false));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }



  public  static Specification<Greens> greensWhere(
          final  Greens greens,
          final  Member member,
          final  GreensClass greensClass
  ){
    return new Specification<Greens>(){
      @Override
      public Predicate toPredicate(Root<Greens> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (greens.getGreensId()!=null && !greens.getGreensId().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("greensId"),greens.getGreensId()));
        }
        if (greens.getGreensName()!=null && !greens.getGreensName().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("greensName"),greens.getGreensName()));
        }
        if (greens.getState()!=null && !greens.getState().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("state"),greens.getState()));
        }
        if (member!=null && StringUtils.isNotEmpty(member.getMemberId())){
          predicates.add(criteriaBuilder.equal(root.<String>get("member"),member));
        }
        if (greensClass!=null && StringUtils.isNotEmpty(greensClass.getGreensClassId())){
          predicates.add(criteriaBuilder.equal(root.<String>get("greensClass"),greensClass));
        }
        predicates.add(criteriaBuilder.equal(root.<Boolean>get("del"),false));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }
}
