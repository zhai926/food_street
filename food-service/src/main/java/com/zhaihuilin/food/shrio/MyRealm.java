package com.zhaihuilin.food.shrio;

import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.entity.role.Permission;
import com.zhaihuilin.food.code.entity.role.Role;
import com.zhaihuilin.food.code.utils.EncryptionUtil;
import com.zhaihuilin.food.service.member.MemberService;
import com.zhaihuilin.food.service.member.RoleService;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Shiro从从Realm获取安全数据（如用户、角色、权限），
 * 就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，
 * 即安全数据源
 */

@Log4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleService roleService;



    //认证.登录  【手机号 或者 用户名 登录】
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();
        String charSequence= String.valueOf(utoken.getPassword());
        log.info("对用户[{}]进行登录验证..验证开始"+username);
        log.info("获取的用户名:" + username +",密码："+ charSequence);

        Member member = new Member();
        member = memberService.findMemberByUsername(username);
        if (member!=null){
            log.info("解密后的:" + EncryptionUtil.base64Decrypt(member.getPassword()));
            return new SimpleAuthenticationInfo(member, EncryptionUtil.base64Decrypt(member.getPassword()),getName());
        }
        member=memberService.findMemberByPhone(username);
        if (member!=null){
            log.info("解密后的:" + EncryptionUtil.base64Decrypt(member.getPassword()));
            return new SimpleAuthenticationInfo(member, EncryptionUtil.base64Decrypt(member.getPassword()),getName());
        }
        //找不到用户返回null
        if (member == null) {
            throw new UnknownAccountException();  // 抛出 帐号找不到异常
        }
        return  null;
    }



    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        Member member = (Member) principal.getPrimaryPrincipal();//获取session中的用户
        //声明一个集合 存储权限集合
        List<String> permissions = new ArrayList<String>();
        List<Role> roles = roleService.findRoleByMember(member);
        if (roles!=null && roles.size()>0){
            for (Role role:roles){
                if (role.getPermissionList()!=null && role.getPermissionList().size()>0){
                    for (Permission permission:role.getPermissionList()){
                        permissions.add(permission.getUrl());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }



    /**
     * 修改授权后清空缓存中的授权信息(修改权限的service中调用)
     */
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
