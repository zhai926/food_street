package com.zhaihuilin.food.persistent.member;

import com.zhaihuilin.food.code.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 用户执行层
 * Created by zhaihuilin on 2018/12/26 15:58.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member,String> ,JpaSpecificationExecutor<Member> {

  // 根据用户编号并且没删除进行查询
  public  Member findMemberByMemberIdAndDelFalse(String memberId);

  // 根据用户姓名并且没删除进行查询
  public  Member findMemberByUsernameAndDelFalse(String userName);

  // 根据用户的openId 进行查询
  public  Member findMemberByOpenIdAndDelFalse(String openId);

  //逻辑删除用户
  @Query(value = "update Member m set m.del = true where m.memberId=?1")
  @Modifying
  public void deleteByMemberId(String memberId);

  // 根据手机号查询用户
  public  Member findMemberByPhone(String phone);

  // 根据用户编号并进行查询
  public  Member findMemberByMemberId(String memberId);

  //根据用户姓名进行查询
  public  Member findMemberByUsername(String userName);




}
