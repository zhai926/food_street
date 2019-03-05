package com.zhaihuilin.food.code.entity.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zhaihuilin.food.code.entity.role.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * Created by zhaihuilin on 2018/12/26 14:57.
 */

@Data
@Entity
@ToString
@Table(name="food_member")
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {

    @Id
    @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
            @Parameter(name = "k",value = "M")
    })
    @GeneratedValue(generator = "sys-uid")
    private  String  memberId;

    /**
     * 用户名
     */
    @NonNull
    @Column(length = 36,unique = true)
    private String username;

    /**
     * 用户密码
     */
    @NonNull
    @Column(length = 36)
    private String password;

    /**
     * 昵称
     */
    @Column(length = 20)
    private String nickName;

    /**
     * 真实姓名
     */
    @Column(length = 10)
    private String name;

    /**
     * 联系电话
     */
    @NonNull
    @Column(length = 13)
    private String phone;

    /**
     * E-mail
     */
    private String eMail;

    /**
     * 用户状态
     */
    private int state;

    /**
     * 是否删除
     */
    @NonNull
    private boolean del = Boolean.FALSE;

    /**
     * 创建时间
     */
    @NonNull
    private long createTime = new  Date().getTime();

    /**
     * 更新时间
     */
    private long updateTime;

    //公众号提供的用户id
    private String openId;


    /**
     * 所拥有的权限
     */
    @ManyToMany(fetch = FetchType.EAGER,cascade = {})
    @JoinTable(name = "food_member_role",joinColumns ={
            @JoinColumn(name = "member_id")
    },inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    })
    @JsonManagedReference
    private List<Role> roleList;



    /**
     * 明星
     */
    //@ManyToOne( cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    //@JoinColumn(name = "starMember_id")
    //@JsonBackReference
    //private Member starMember;
    //
    ///**
    // * 粉丝       一个粉丝可以关注多个明星
    // */
    //@JsonManagedReference
    //@Transient
    //private List<Member> fansMember;


    public Member(String password, String phone,int state) {
        this.password = password;
        this.phone = phone;
        this.state = state;
    }





    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", eMail='" + eMail + '\'' +
                ", state='" + state + '\'' +
                ", del=" + del +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", openId='" + openId + '\'' +
                '}';
    }
}
