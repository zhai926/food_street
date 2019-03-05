package com.zhaihuilin.food.front.controller.greens;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.front.utils.CheckUserUtil;
import com.zhaihuilin.food.greens.GreensClassService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/4 10:12.
 */
@RestController
@RequestMapping(value = "/api/greensclass")
@Log4j
public class GreensClassController {

  @Autowired
  private GreensClassService greensClassService;

  @Autowired
  private CheckUserUtil checkUserUtil;


  /**
   * 获取所有的一级分类
   * @param request
   * @return
   */
  @PostMapping(value = "/findByOneClass")
  public  ReturnMessages findByOldClassIsNotNullAndDelFalse(
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    List<GreensClass> greensClassList=greensClassService.findByOldClassIsNullAndDelFalse();
    if (greensClassList!=null && greensClassList.size()>0){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取成功！",greensClassList);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取成功！",null);
      return returnMessages;
    }
  }

  /**
   * 获取所有的二级分类
   * @param request
   * @return
   */
  @PostMapping(value = "/findSecondClass")
  public  ReturnMessages findSecondClass(
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    List<GreensClass> greensClassList=greensClassService.findByOldClassIsNotNullAndDelFalse();
    if (greensClassList!=null && greensClassList.size()>0){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取成功！",greensClassList);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取成功！",null);
      return returnMessages;
    }
  }
  /**
   * 获取一级分类下二级分类
   * @param parentClassId  一级分类编号
   * @param request
   * @return
   */
  @PostMapping(value = "/findScByOne")
  public ReturnMessages findSecondClassByOneclass(
      @RequestParam(name = "parentClassId",required = true)String parentClassId,
      HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(parentClassId)){
       returnMessages = new ReturnMessages(RequestState.ERROR,"父级编号不能为空！",null);
       return returnMessages;
    }
    GreensClass oldClass=greensClassService.findByGreensClassIdAndDelFalse(parentClassId);
    if (oldClass==null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"父级分类不存在！",null);
       return returnMessages;
    }
    // 根据父级获取二级分类
    List<GreensClass> secondClassList=greensClassService.findGreensClassByOldClassAndDelFalse(oldClass);
    if (secondClassList!=null && secondClassList.size()>0){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取二级分类成功！",secondClassList);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"获取二级分类失败！",null);
      return returnMessages;
    }
  }
}
