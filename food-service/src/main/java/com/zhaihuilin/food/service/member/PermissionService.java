package com.zhaihuilin.food.service.member;

import com.zhaihuilin.food.code.entity.role.Permission;

import java.util.List;

/**
 * Created by zhaihuilin on 2018/12/29 11:28.
 */
public interface PermissionService {

  //查询所有的权限信息
  public List<Permission> findAllByPermission();

  // 根据权限编号进行查询
  public Permission findById(long id);

  //保存权限信息
  public Permission savePermission(Permission permission);

  //编辑权限信息
  public Permission updatePermission(Permission permission);

  //根据权限编号进行删除
  public boolean deletePermission(long id);
}
