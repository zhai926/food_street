package com.zhaihuilin.food.service.member;

import com.zhaihuilin.food.code.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaihuilin on 2018/12/26 16:14.
 */

public interface MemberService {

  // 根据用户编号进行查询
  public Member findMemberByMemberIdAndDelFalse(String memberId);

  // 根据用户姓名进行查询
  public  Member findMemberByUsernameAndDelFalse(String userName);

  // 根据用户的openId 进行查询
  public  Member findMemberByOpenIdAndDelFalse(String openId);

  //逻辑删除用户
  public boolean deleteByMemberId(String memberId);

  // 保存用户信息
  public  Member saveMember(Member member);

  //编写用户信息
  public  Member updateMember(Member member);

  /**
   * 物理删除
   * @param memberId
   * @return
   */
  public boolean physicallyDeleteByMemberId(String memberId);

  /**
   * 查询用户是否存在
   * @param username
   * @return
   */
  public boolean existMemberbyUsername(String username);

  // 检查手机号是否使用
  public  boolean exisMemberByPhone(String phone);


  // 根据用户编号并进行查询
  public  Member findMemberByMemberId(String memberId);

  //根据用户姓名进行查询
  public  Member findMemberByUsername(String userName);

  // 根据手机号查询用户
  public  Member findMemberByPhone(String phone);


  /**
   * 根据条件查询
   * @param member 条件查询
   * @param pageable 翻页
   * @return
   */
  public Page<Member> findAllByMember(Member member, Pageable pageable);


  /**
   * 根据条件查询
   * @param member
   * @return
   */
  public List<Member> findAllByMember(Member member);
}
