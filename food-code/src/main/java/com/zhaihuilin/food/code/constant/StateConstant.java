package com.zhaihuilin.food.code.constant;

/**
 * 菜谱状态常量
 * Created by zhaihuilin on 2019/1/3 15:07.
 */
public enum  StateConstant {

  /**
   * 菜谱状态
   */
  GREENS_STATE_ON_CHECKING,  //审核中
  GREENS_STATE_CHECK_ON, //审核通过
  GREENS_STATE_CHECK_OFF, //审核不通过
  GREENS_STATE_HOUSE, //待发布 也就是草稿箱中的


  /**
   * 修改菜谱状态
   */
  MODIFY_GREENS_STATE_ON_CHECKING,  //修改审核中
  MODIFY_GREENS_STATE_CHECK_ON, //修改审核通过
  MODIFY_GREENS_STATE_CHECK_OFF, //修改审核不通过

  /**
   * 菜系状态
   */
  STYLE_STATE_ON_CHECKING,  //菜系审核中
  STYLE_STATE_CHECK_ON,    //菜系审核通过（正常使用）
  STYLE_STATE_CHECK_OFF, //菜系审核不通过
  STYLE_STATE_ON_CLOSE,    //菜系停用

  /**
   * 菜谱评论状态(后台暂无评论审核功能，评论保存时直接审核通过)
   */
  COMMENT_STATE_ON_CHECKING,  //评论审核中
  COMMENT_STATE_CHECK_ON,    //评论审核通过（显示）
  COMMENT_STATE_CHECK_OFF, //评论审核不通过

  /**
   * 热门活动状态
   */
  HOT_ACT_STATE_ON_WAY ,// 进行中
  HOT_ACT_STATE_ON_STOP,// 停止


}
