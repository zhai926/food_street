package com.zhaihuilin.food.front.controller.collect;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.collect.Collect;
import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.utils.GreensUtils;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.code.vo.GreensVo;
import com.zhaihuilin.food.collect.CollectService;
import com.zhaihuilin.food.front.utils.CheckUserUtil;
import com.zhaihuilin.food.greens.GreensService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * Created by zhaihuilin on 2019/1/8 15:14.
 */
@RestController
@RequestMapping(value = "/api/collect")
@Log4j
//@Api(value = "我的收藏接口",tags = {"我的收藏接口"})  //修饰整个类，描述Controller的作用
public class CollectController {
  @Autowired
  private CheckUserUtil checkUserUtil;

  @Autowired
  private CollectService collectService;

  @Autowired
  private GreensService greensService;

  /**
   *   @ApiImplicitParams :用在方法上包含一组参数说明
   * 新增收藏
   * @param greensId  菜谱编号
   * @return
   */

  //@ApiOperation(value = "新增我的收藏",notes = "在新增的时候需要传入一个菜谱编号")  // 描述一个类的一个方法，或者说一个接口
  @PostMapping(value = "/savecollect")
  public ReturnMessages saveCollect(
    //@ApiParam(name = "greensId", value = "菜单编号",required = true)
    @RequestParam(value = "greensId") String greensId
  ){
     ReturnMessages  returnMessages = null;
     Collect collect = new Collect();
     //判断用户是否登录
     returnMessages=checkUserUtil.returnMessages();
     if (returnMessages!=null){
        return returnMessages;
     }
     //获取当前登录的用户
     Member member  =checkUserUtil.getMember();
     if (!StringUtils.isNotEmpty(greensId)){
       returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱编号不能为空！",null);
       return returnMessages;
     }
     Greens greens =greensService.findByGreensIdAndDelFalse(greensId);
     if (greens==null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱不存在！",null);
       return returnMessages;
     }
     // 判断是否收藏过
     boolean flag = collectService.existByGreensId(greensId,member.getMemberId());
     if (flag){
       returnMessages = new ReturnMessages(RequestState.ERROR,"你已经收藏过该菜谱了！",null);
       return returnMessages;
     }
     List<GreensVo> greensVoList = new ArrayList<>();
     GreensVo greensVo =  GreensUtils.toGoodsVo(greens);
     greensVoList.add(greensVo);
     collect.setGreensVoList(greensVoList);
     collect.setMember(member);
     collect = collectService.saveCollect(collect);
     if (collect!=null){
       returnMessages = new ReturnMessages(RequestState.SUCCESS,"收藏成功！",collect);
       return returnMessages;
     }else {
       returnMessages = new ReturnMessages(RequestState.ERROR,"收藏失败！",null);
       return returnMessages;
     }
  }


  /**
   * 取消收藏
   * @param collectId
   * @return
   */
  @PostMapping(value = "/deletecollect")
  public ReturnMessages deleteCollect(
          @RequestParam(value = "collectId") String collectId
  ){
    ReturnMessages  returnMessages = null;
    //判断用户是否登录
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return returnMessages;
    }
    if (!StringUtils.isNotEmpty(collectId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编号不存在！",null);
      return returnMessages;
    }
    Collect collect=  collectService.findByCollectId(collectId);
    if (collect ==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"不存在该收藏！",null);
      return returnMessages;
    }

    boolean flag = collectService.deleteById(collectId);
    if (flag){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"取消收藏成功！",collect);
      return returnMessages;
    }else {
      returnMessages = new ReturnMessages(RequestState.ERROR,"取消收藏失败！",null);
      return returnMessages;
    }
  }

  /**
   * 获取用户自己的收藏
   * @return
   */
  @PostMapping(value = "/findMeCollect")
  public ReturnMessages findMeCollect(){
    ReturnMessages  returnMessages = null;
    //判断用户是否登录
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return returnMessages;
    }
    //获取当前登录的用户
    Member member  =checkUserUtil.getMember();
    List<Collect> collectList =collectService.findAllByMember(member);
    if (collectList!=null && collectList.size()>0){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"查询成功！",collectList);
      return returnMessages;
    }else {
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"暂无收藏！",null);
      return returnMessages;
    }
  }
}
