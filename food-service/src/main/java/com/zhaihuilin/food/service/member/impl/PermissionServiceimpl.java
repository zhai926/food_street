package com.zhaihuilin.food.service.member.impl;

import com.zhaihuilin.food.code.entity.role.Permission;
import com.zhaihuilin.food.persistent.member.PermissionRepository;
import com.zhaihuilin.food.service.member.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaihuilin on 2018/12/29 11:29.
 */
@Service
public class PermissionServiceimpl implements PermissionService {

  @Autowired
  PermissionRepository permissionRepository;


  @Override
  public List<Permission> findAllByPermission() {
    return permissionRepository.findAllByOldIsNull();
  }

  @Override
  public Permission findById(long id) {
    return permissionRepository.findOne(id);
  }

  @Override
  public Permission savePermission(Permission permission) {
    return  permissionRepository.save(permission);
  }

  @Override
  public Permission updatePermission(Permission permission) {
    return permissionRepository.save(permission);
  }

  @Override
  public boolean deletePermission(long id) {
    try {
      permissionRepository.delete(id);
      return true;
    }catch (Exception e){
      return false;
    }

  }
}
