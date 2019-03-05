package com.zhaihuilin.food.persistent.member;

import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色 持久层
 * Created by zhaihuilin on 2018/12/29 11:21.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

  /**
   * 根据角色id查询角色
   * @param id
   * @return
   */
  public Role findRoleById(long id);

  /**
   * 根据用户名查询
   * @param roleName
   * @return
   */
  public List<Role> findRoleByRoleName(String roleName);


  /**
   * 设定默认角色
   * @param id
   * @return
   */
  @Query(value = "update Role r set r.theDefault = true where r.id = ?1")
  public Role setRoleDefault(long id);

  /**
   * 查询默认角色
   * @return
   */
  public Role findRoleByTheDefaultTrue();


  /**
   * 通过用户查询角色
   * @return
   */
  public List<Role> findRoleByMemberList(List<Member> members);
}
