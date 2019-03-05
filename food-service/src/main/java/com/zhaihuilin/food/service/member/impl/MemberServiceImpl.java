package com.zhaihuilin.food.service.member.impl;

import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.persistent.member.MemberRepository;
import com.zhaihuilin.food.service.member.MemberService;
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
 * Created by zhaihuilin on 2018/12/26 16:24.
 */
@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberRepository memberRepository;



  @Override
  public Member findMemberByMemberIdAndDelFalse(String memberId) {
    return memberRepository.findMemberByMemberIdAndDelFalse(memberId);
  }

  @Override
  public Member findMemberByUsernameAndDelFalse(String userName) {
    return memberRepository.findMemberByUsernameAndDelFalse(userName);
  }

  @Override
  public Member findMemberByOpenIdAndDelFalse(String openId) {
    return memberRepository.findMemberByOpenIdAndDelFalse(openId);
  }

  @Override
  public boolean deleteByMemberId(String memberId) {
    boolean flag = Boolean.TRUE;
    try {
      Member member = findMemberByMemberId(memberId);
      if (member!=null){
        memberRepository.deleteByMemberId(memberId);
        flag = Boolean.TRUE;
      }
    }catch (Exception e){
      flag = Boolean.FALSE;
    }
    return  flag;
  }

  @Override
  public Member saveMember(Member member) {
    return memberRepository.save(member);
  }

  @Override
  public Member updateMember(Member member) {
    return memberRepository.save(member);
  }


  @Override
  public boolean physicallyDeleteByMemberId(String memberId) {

    boolean flag = Boolean.TRUE;
    try {
      Member member = findMemberByMemberId(memberId);
      if (member!=null){
        memberRepository.delete(memberId);
        flag = Boolean.TRUE;
      }
    }catch (Exception e){
      flag = Boolean.FALSE;
    }
    return  flag;
  }

  @Override
  public boolean existMemberbyUsername(String username) {
    boolean flag = Boolean.FALSE;
    Member member = memberRepository.findMemberByUsername(username);
    if (member != null){
      flag = Boolean.TRUE;
    }
    return flag;
  }

  @Override
  public boolean exisMemberByPhone(String phone) {
    boolean flag = Boolean.FALSE;
    Member member =memberRepository.findMemberByPhone(phone);
    if (member!=null){
      flag = Boolean.TRUE;
    }
    return flag;
  }

  @Override
  public Member findMemberByMemberId(String memberId) {
    return memberRepository.findMemberByMemberId(memberId);
  }

  @Override
  public Member findMemberByUsername(String userName) {
    return memberRepository.findMemberByUsername(userName);
  }

  @Override
  public Member findMemberByPhone(String phone) {
    return memberRepository.findMemberByPhone(phone);
  }


  @Override
  public Page<Member> findAllByMember(Member member, Pageable pageable) {
    return memberRepository.findAll(memberWhere(member),pageable);
  }

  @Override
  public List<Member> findAllByMember(Member member) {
    return memberRepository.findAll(memberWhere(member));
  }

  /**
   * 条件查询
   * @param member
   * @return
   */
  public static Specification<Member> memberWhere(
          final Member member
  ){
    return new Specification<Member>() {
      @Override
      public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if(member.getMemberId() != null && !member.getMemberId().equals("")){
          predicates.add(cb.equal(root.<String>get("memberId"),member.getMemberId()));
        }
        if(member.getUsername() != null && !member.getUsername().equals("")){
          predicates.add(cb.like(root.<String>get("username"),"%"+member.getUsername()+"%"));
        }
        if(member.getNickName() != null && !member.getNickName().equals("")){
          predicates.add(cb.like(root.<String>get("nickName"),"%"+member.getNickName()+"%"));
        }
        if(StringUtils.isNotEmpty(member.getName())){
          predicates.add(cb.like(root.<String>get("name"),"%"+member.getName()+"%"));
        }
        if (member.getEMail() != null && !member.getEMail().equals("")){
          predicates.add(cb.like(root.<String>get("eMail"),"%"+member.getEMail()+"%"));
        }
        if(member.getPhone() != null && !member.getPhone().equals("")){
          predicates.add(cb.like(root.<String>get("phone"),"%"+member.getPhone()+"%"));
        }
        predicates.add(cb.equal(root.<Boolean>get("del"),member.isDel()));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }



  /**
   * 条件查询
   * @param member
   * @return
   */
  public static Specification<Member> memberByUsernameOrPhoneWhere(
          final Member member
  ){
    return new Specification<Member>() {
      @Override
      public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if(member.getUsername() != null && !member.getUsername().equals("")){
          predicates.add(cb.like(root.<String>get("username"),member.getUsername()));
        }
        if(member.getPhone() != null && !member.getPhone().equals("")){
          predicates.add(cb.like(root.<String>get("phone"),member.getPhone()));
        }
        predicates.add(cb.equal(root.<Boolean>get("del"),member.isDel()));
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }
    };
  }
}
