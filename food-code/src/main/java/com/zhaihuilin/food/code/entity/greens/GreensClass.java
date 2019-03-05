package com.zhaihuilin.food.code.entity.greens;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

/**
 * 菜谱分类
 * Created by zhaihuilin on 2019/1/3 15:11.
 */
@Data
@Entity
@ToString
@Table(name="food_greens_class")
@NoArgsConstructor
@AllArgsConstructor
public class GreensClass implements Serializable {


  @Id
  @GenericGenerator(name = "sys-uid",strategy = "com.zhaihuilin.food.code.utils.KeyGeneratorUtils",parameters = {
          @Parameter(name = "k",value = "GC")
  })
  @GeneratedValue(generator = "sys-uid")
  private  String greensClassId;

  //菜谱分类名称
  @NonNull
  private  String greensClassName;

  //创建时间
  private  long  createTime = new  Date().getTime();

  //编辑时间
  private  long updateTime;


  //是否删除
  @NonNull
  private boolean del= Boolean.FALSE;

  /**
   * 父类
   */
  @ManyToOne( cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
  @JoinColumn(name = "oldClass_id")
  @JsonBackReference
  private GreensClass oldClass;

  /**
   * 子类
   */
  @JsonManagedReference
  @Transient
  private List<GreensClass> childClass;

}
