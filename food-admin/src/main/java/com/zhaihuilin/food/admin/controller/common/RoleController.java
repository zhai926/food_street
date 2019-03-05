package com.zhaihuilin.food.admin.controller.common;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.role.Permission;
import com.zhaihuilin.food.code.entity.role.Role;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.service.member.PermissionService;
import com.zhaihuilin.food.service.member.RoleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色
 * Created by zhaihuilin on 2018/12/29 13:47.
 */
@RestController
@RequestMapping(value = "/api/role")
@Log4j
public class RoleController  {

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    /**
     * 查询角色
     * @param id 角色ID[可空]
     * @return
     */
    @PostMapping(value = "/find")
    public ReturnMessages findRoleByID(
            @RequestParam(name = "id",required = false,defaultValue = "-1")long id
    ){
        if(id > 0){
            Role role = roleService.findRoleById(id);
            if(role!=null){
                return new ReturnMessages(RequestState.SUCCESS,"查询成功.",role);
            }else{
                return new ReturnMessages(RequestState.ERROR,"查询失败.",null);
            }
        }else{
            List<Role> roles = roleService.findRoleAll();
            if(roles.size() > 0){
                return new ReturnMessages(RequestState.SUCCESS,"查询成功.",roles);
            }else{
                return new ReturnMessages(RequestState.ERROR,"查询失败.",null);
            }
        }
    }

    /**
     * 删除角色
     * @param id 角色ID
     * @return
     */
    @PostMapping(value = "/delete")
    public ReturnMessages deleteRoleById(
            @RequestParam(name = "id")long id
    ){
        Role role = roleService.findRoleById(id);
        if(role != null){
            List<Permission> permissions = role.getPermissionList();
            if (permissions != null && permissions.size() > 0){
                for (Permission permission : permissions){
                    List<Role> roles = permission.getRoleList();
                    int index  = roles.indexOf(role);
                    if(index >= 0){
                        roles.remove(index);
                    }
                    permission.setRoleList(roles);
                    permissionService.savePermission(permission);
                }
            }
        }
        if (roleService.deleteRole(id)){
            return new ReturnMessages(RequestState.SUCCESS,"删除成功.",true);
        }else{
            return new ReturnMessages(RequestState.ERROR,"删除失败.",false);
        }
    }

    /**
     * 创建角色
     * @param name 角色名称
     * @param roleCode 角色身份标识(ROLE:ADMIN:SUPER属于超级管理员无限配置权限)
     * @param pIds 所有权限
     * @param theDefault 是否为默认角色
     * @return
     */
    @PostMapping(value = "/save")
    public ReturnMessages saveRole(
            @RequestParam(name = "name")String name,
            @RequestParam(name = "roleCode")String roleCode,
            @RequestParam(name = "roleDesc",required = false)String roleDesc,
            @RequestParam(name = "pIds",required = false)long[] pIds,
            @RequestParam(name = "default" ,required = false,defaultValue = "false") boolean theDefault
    ){
        List<Permission> permissions = new ArrayList<Permission>();
        Role role = new Role();
        if(pIds != null){
            for (long pid : pIds){
                Permission permission = permissionService.findById(pid);
                if(permission != null){
                    permissions.add(permission);
                }
            }
        }
        role.setRoleName(name);
        role.setRoleCode(roleCode);
        if (StringUtils.isNotEmpty(roleDesc)){
            role.setRoleDesc(roleDesc);
        }
        role.setCreateDate(new Date());
        if(permissions.size() > 0){
            role.setPermissionList(permissions);
        }
        role = roleService.saveRole(role);
        if(pIds!= null){
            List<Permission> permissionList = role.getPermissionList();
            if(permissionList != null && permissionList.size() > 0){
                for (Permission permission:permissionList
                        ) {
                    List<Role> roles = permission.getRoleList();
                    int index = roles.indexOf(role);
                    if(index >= 0){
                        roles.remove(index);
                        permission.setRoleList(roles);
                        permission = permissionService.updatePermission(permission);
                    }
                }
            }
            for (long pId : pIds){
                Permission permission = permissionService.findById(pId);
                List<Role> roles = permission.getRoleList();
                if(roles.indexOf(role) < 0){
                    roles.add(role);
                    permission.setRoleList(roles);
                    permission = permissionService.updatePermission(permission);
                }
            }
        }
        if(role != null){
            if(theDefault){
                roleService.setRoleDefault(role.getId());
            }
            return new ReturnMessages(RequestState.SUCCESS,"创建角色成功.",role);
        }else{
            return new ReturnMessages(RequestState.ERROR,"创建角色失败.",null);
        }
    }

    /**
     * 编辑角色
     * @param id 角色ID
     * @param name 角色名称
     * @param roleCode 角色身份标识(ROLE:ADMIN:SUPER属于超级管理员无限配置权限)
     * @param pIds 所有权限
     * @param theDefault 是否为默认角色
     * @return
     */
    @PostMapping(value = "/update")
    public ReturnMessages updateRole(
            @RequestParam(name = "id")long id,
            @RequestParam(name = "name",required = false)String name,
            @RequestParam(name = "roleCode",required = false)String roleCode,
            @RequestParam(name = "pIds",required = false)long[] pIds,
            @RequestParam(name = "default" ,required = false,defaultValue = "false") boolean theDefault
    ){
        Role role = roleService.findRoleById(id);
        List<Permission> permissions = new ArrayList<Permission>();
        if(role == null){
            return new ReturnMessages(RequestState.ERROR,"未查到该角色.",null);
        }
        if(StringUtils.isNotEmpty(name)){
            role.setRoleName(name);
        }
        if(StringUtils.isNotEmpty(roleCode)){
            role.setRoleCode(roleCode);
        }
        role = roleService.saveRole(role);
        if(pIds!= null){
            List<Permission> permissionList = role.getPermissionList();
            if(permissionList != null && permissionList.size() > 0){
                for (Permission permission:permissionList
                        ) {
                    List<Role> roles = permission.getRoleList();
                    int index = roles.indexOf(role);
                    if(index >= 0){
                        roles.remove(index);
                        permission.setRoleList(roles);
                        permission = permissionService.updatePermission(permission);
                    }
                }
            }
            for (long pId : pIds){
                Permission permission = permissionService.findById(pId);
                List<Role> roles = permission.getRoleList();
                if(roles.indexOf(role) < 0){
                    roles.add(role);
                    permission.setRoleList(roles);
                    permission = permissionService.updatePermission(permission);
                }
            }
        }
        if(role != null){
            if(theDefault){
                roleService.setRoleDefault(role.getId());
            }
            return new ReturnMessages(RequestState.SUCCESS,"角色编辑成功.",role);
        }else{
            return new ReturnMessages(RequestState.ERROR,"角色编辑失败.",role);
        }
    }

    /**
     * 通过角色ID获取权限
     * @param roleId 角色ID
     * @return
     */
    @PostMapping(value = "/findPermission")
    public ReturnMessages findPermissionByRoleId(@RequestParam(value = "roleId")long roleId){
        Role role = roleService.findRoleById(roleId);
        if(role!= null){
            List<Permission> permissions = role.getPermissionList();
            if(permissions != null && permissions.size() > 0){
                return new ReturnMessages(RequestState.SUCCESS,"查询成功。",permissions);
            }
        }
        return new ReturnMessages(RequestState.ERROR,"为查找到任何关联权限。",null);
    }

    /**
     * 设置为默认角色
     * @param id 角色ID
     * @return
     */
    @PostMapping(value = "/setTheDefault")
    public ReturnMessages setTheDefault(
            @RequestParam(value = "id")long id
    ){
        Role role = roleService.setRoleDefault(id);
        if(role != null){
            return new ReturnMessages(RequestState.SUCCESS,"设置成功。",role);
        }else{
            return new ReturnMessages(RequestState.ERROR,"设置失败。",null);
        }
    }
    
}
