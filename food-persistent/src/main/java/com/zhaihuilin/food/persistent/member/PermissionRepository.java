package com.zhaihuilin.food.persistent.member;

import com.zhaihuilin.food.code.entity.role.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 权限持久层
 * Created by zhaihuilin on 2018/12/29 11:24.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

  public List<Permission> findAllByOldIsNull();
}
