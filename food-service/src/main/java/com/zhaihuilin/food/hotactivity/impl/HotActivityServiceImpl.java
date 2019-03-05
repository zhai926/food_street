package com.zhaihuilin.food.hotactivity.impl;

import com.zhaihuilin.food.code.entity.hotactivity.HotActivity;
import com.zhaihuilin.food.hotactivity.HotActivityService;
import com.zhaihuilin.food.persistent.hotactivity.HotActivityRepository;
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
import java.util.Date;
import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/8 13:32.
 */
@Service
public class HotActivityServiceImpl implements HotActivityService {

  @Autowired
  private HotActivityRepository hotActivityRepository;

  @Override
  public HotActivity findByHotActIdAndDelFalse(String hotActId) {
    return hotActivityRepository.findByHotActIdAndDelFalse(hotActId);
  }

  @Override
  public HotActivity findByHotActivitytitleTrue(String hotActivitytitle) {
    return hotActivityRepository.findByHotActivitytitleTrue(hotActivitytitle);
  }

  @Override
  public Page<HotActivity> findAll(HotActivity hotActivity, Pageable pageable) {
    return hotActivityRepository.findAll(hotActivitywhere(hotActivity),pageable);
  }

  @Override
  public Page<HotActivity> findAll(HotActivity hotActivity, Long startDate, Long endDate,Pageable pageable) {
    return hotActivityRepository.findAll(wherehotActivity(hotActivity,startDate,endDate),pageable);
  }

  @Override
  public List<HotActivity> findList(HotActivity hotActivity) {
    return hotActivityRepository.findAll(hotActivitywhere(hotActivity));
  }

  @Override
  public HotActivity saveHotActivity(HotActivity hotActivity) {
    return hotActivityRepository.save(hotActivity);
  }

  @Override
  public HotActivity updateHotActivity(HotActivity hotActivity) {
    return hotActivityRepository.save(hotActivity);
  }

  @Override
  public boolean deleteById(String hotActId) {
    HotActivity hotActivity =findByHotActIdAndDelFalse(hotActId);
    if (hotActivity!=null){
       hotActivityRepository.deleteHotActivityByHotActId(hotActId);
       return true;
    }
    return false;
  }

  @Override
  public boolean physicallyDeleteByHotActId(String hotActId) {
    HotActivity hotActivity =findByHotActIdAndDelFalse(hotActId);
    if (hotActivity!=null){
      hotActivityRepository.delete(hotActId);
      return true;
    }
    return false;
  }

  //条件
  public  static Specification<HotActivity> wherehotActivity(
          final  HotActivity hotActivity,
          final  Long startDate,
          final  Long endDate
  ){
    return new Specification<HotActivity>(){
      @Override
      public Predicate toPredicate(Root<HotActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (hotActivity.getHotActId()!=null && !hotActivity.getHotActId().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("hotActId"),hotActivity.getHotActId()));
        }
        if (hotActivity.getHotActivitytitle()!=null && !hotActivity.getHotActivitytitle().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("hotActivitytitle"),hotActivity.getHotActivitytitle()));
        }
        if (hotActivity.getState()!=null && !hotActivity.getState().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("state"),hotActivity.getState()));
        }
        if (startDate != null && endDate != null && (startDate <=endDate)){
          predicates.add(criteriaBuilder.between(root.<Long>get("createTime"),startDate,endDate));
        }else if(startDate !=null && endDate ==null){
          predicates.add(criteriaBuilder.between(root.<Long>get("createTime"),startDate,new Date().getTime()));
        }else if (startDate == null && endDate != null){
          predicates.add(criteriaBuilder.between(root.<Long>get("createTime"),0l,endDate));
        }
        predicates.add(criteriaBuilder.equal(root.<Boolean>get("del"),false));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }


  //条件查询
  public  static Specification<HotActivity> hotActivitywhere(
          final  HotActivity hotActivity
  ){
    return new Specification<HotActivity>(){
      @Override
      public Predicate toPredicate(Root<HotActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (hotActivity.getHotActId()!=null && !hotActivity.getHotActId().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("hotActId"),hotActivity.getHotActId()));
        }
        if (hotActivity.getHotActivitytitle()!=null && !hotActivity.getHotActivitytitle().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("hotActivitytitle"),hotActivity.getHotActivitytitle()));
        }
        if (hotActivity.getState()!=null && !hotActivity.getState().equals("")){
          predicates.add(criteriaBuilder.equal(root.<String>get("state"),hotActivity.getState()));
        }
        predicates.add(criteriaBuilder.equal(root.<Boolean>get("del"),false));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }
}
