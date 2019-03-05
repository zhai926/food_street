package com.zhaihuilin.food.service.member.impl;

import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.entity.role.Role;
import com.zhaihuilin.food.persistent.member.MemberRepository;
import com.zhaihuilin.food.persistent.member.RoleRepository;
import com.zhaihuilin.food.service.member.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaihuilin on 2018/12/29 11:53.
 */
@Service
public class RoleServiceImpl implements RoleService {


  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Override
  public Role findRoleById(long id) {
    return roleRepository.findRoleById(id);
  }

  @Override
  public List<Role> findRoleAll() {
    return roleRepository.findAll();
  }

  @Override
  public List<Role> findRoleByName(String name) {
    return roleRepository.findRoleByRoleName(name);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public Role updateRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public boolean deleteRole(long id) {
    try {
      roleRepository.delete(id);
      return true;
    }catch (Exception e){
      return false;
    }

  }

  @Override
  public Role setRoleDefault(long id) {
    Role role = roleRepository.findRoleByTheDefaultTrue();
    if(role != null){
      role.setTheDefault(false);
      roleRepository.save(role);
    }
    role = roleRepository.findRoleById(id);
    role.setTheDefault(true);
    return roleRepository.save(role);
  }

  @Override
  public Role getRoleByDefaule() {
    return roleRepository.findRoleByTheDefaultTrue();
  }

  @Override
  public List<Role> findRoleByMember(Member member) {
    List<Member> members = new ArrayList<Member>();
    members.add(member);
    return roleRepository.findRoleByMemberList(members);
  }

  @Override
  public boolean setMemberRole(String username, List<Role> roles) {
    Member member = memberRepository.findMemberByUsername(username);
    if(member != null){
      member.setRoleList(roles);
      member = memberRepository.save(member);
      if(member != null){
        return true;
      }
    }
    return false;
  }


  @Override
  public boolean setMemberRoleByPhone(String phone, List<Role> roles) {
    Member member = memberRepository.findMemberByPhone(phone);
    if(member != null){
      member.setRoleList(roles);
      member = memberRepository.save(member);
      if(member != null){
        return true;
      }
    }
    return false;
  }
}
