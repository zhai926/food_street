package com.zhaihuilin.food.code.entity.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaihuilin.food.code.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 * Created by zhaihuilin on 2018/12/29 10:50.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food_role")
public class Role implements Serializable {

  //角色Id
  @Id
  @GeneratedValue
  private long id;

  //角色编码 暂时无用方便后期扩展
  private String roleCode;

  //角色名称
  private String roleName;

  //默认角色
  private boolean theDefault;

  //角色描述
  private String roleDesc;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateDate;

  // 用户集合
  @ManyToMany(mappedBy = "roleList")
  @JsonBackReference
  private List<Member> memberList;

  // 权限集合
  @ManyToMany(mappedBy = "roleList",fetch = FetchType.EAGER)
  @JsonBackReference
  private List<Permission> permissionList;


  @Override
  public String toString() {
    return "Role{" +
            "id=" + id +
            ", roleCode='" + roleCode + '\'' +
            ", roleName='" + roleName + '\'' +
            ", theDefault=" + theDefault +
            ", roleDesc='" + roleDesc + '\'' +
            ", createDate=" + createDate +
            ", updateDate=" + updateDate +
            '}';
  }
}
