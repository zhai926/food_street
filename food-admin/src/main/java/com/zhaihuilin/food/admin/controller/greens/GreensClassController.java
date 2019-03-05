package com.zhaihuilin.food.admin.controller.greens;

import com.zhaihuilin.food.admin.utils.CheckUserUtil;
import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.greens.GreensClassService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
   * 新增菜谱分类
   * @param greensClassName   菜谱分类名称
   * @param parentClassId   菜谱分类父级编号
   * @param request
   * @return
   */
  @PostMapping(value ="/saveclass")
  public ReturnMessages saveGreensClass(
     @RequestParam(value = "greensClassName") String greensClassName,
     @RequestParam(name = "parentClassId",required = false)String parentClassId,
     HttpServletRequest request
  ){
    GreensClass greensClass= new GreensClass();
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    //判断
    if (!StringUtils.isNotEmpty(greensClassName)){
       returnMessages = new ReturnMessages(RequestState.ERROR,"分类名称不能为空！",null);
       return returnMessages;
    }
    if (greensClassService.existGreensClassByName(greensClassName)){
       returnMessages = new ReturnMessages(RequestState.ERROR,"分类名称已存在！",null);
       return returnMessages;
    }
    if (StringUtils.isNotEmpty(parentClassId)){
       GreensClass oldClass=greensClassService.findByGreensClassIdAndDelFalse(parentClassId);
       if (oldClass!= null){
         greensClass.setOldClass(oldClass);
       }else{
         returnMessages = new ReturnMessages(RequestState.ERROR,"父级分类不存在！",null);
         return returnMessages;
       }
    }
    greensClass.setGreensClassName(greensClassName);
    //保存
    greensClass=greensClassService.saveGreensClass(greensClass);
    if (greensClass!=null){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"新增成功！",greensClass);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"新增失败！",null);
      return returnMessages;
    }
  }

  /**
   * 编辑菜谱分类消息
   * @param greensClassId      菜谱分类编号
   * @param greensClassName    菜谱分类名称
   * @param parentClassId   菜谱分类父级编号
   * @param request
   * @return
   */
  @PostMapping(value = "/updateclass")
  public ReturnMessages updateGreensClass(
          @RequestParam(value = "greensClassId") String greensClassId,
          @RequestParam(value = "greensClassName",required = false) String greensClassName,
          @RequestParam(name = "parentClassId",required = false)String parentClassId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    //判断
    if (!StringUtils.isNotEmpty(greensClassId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"分类编号不能为空！",null);
      return returnMessages;
    }
    GreensClass greensClass = greensClassService.findByGreensClassIdAndDelFalse(greensClassId);
    if (greensClass==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱分类不存在！",null);
      return returnMessages;
    }
    if (greensClassService.existGreensClassByName(greensClassName)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"分类名称已存在！",null);
      return returnMessages;
    }
    if (StringUtils.isNotEmpty(greensClassName)){
        greensClass.setGreensClassName(greensClassName);
    }
    if (StringUtils.isNotEmpty(parentClassId)){
      GreensClass oldClass=greensClassService.findByGreensClassIdAndDelFalse(parentClassId);
      if (oldClass!= null){
        greensClass.setOldClass(oldClass);
      }else{
        returnMessages = new ReturnMessages(RequestState.ERROR,"父级分类不存在！",null);
        return returnMessages;
      }
    }
    greensClass.setUpdateTime(new Date().getTime());
    //保存
    greensClass=greensClassService.updateGreensClass(greensClass);
    if (greensClass!=null){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"编辑成功！",greensClass);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"编辑失败！",null);
      return returnMessages;
    }
  }


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


  /**
   * 删除分类  逻辑删除
   * @param greensClassId   分类名称
   * @param request
   * @return
   */
  @PostMapping(value = "/deleteClass")
  public ReturnMessages deleteClass(
          @RequestParam(value = "greensClassId") String greensClassId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    //判断
    if (!StringUtils.isNotEmpty(greensClassId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"分类编号不能为空！",null);
      return returnMessages;
    }
    GreensClass greensClass = greensClassService.findByGreensClassIdAndDelFalse(greensClassId);
    if (greensClass==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱分类不存在！",null);
      return returnMessages;
    }
    boolean flag =greensClassService.deleteByClassId(greensClassId);
    if (flag){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",greensClass);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",greensClass);
      return returnMessages;
    }
  }


  /**
   * 删除分类  物理删除
   * @param greensClassId   分类名称
   * @param request
   * @return
   */
  @PostMapping(value = "/physicallyDeleteClass")
  public ReturnMessages physicallyDeleteClass(
          @RequestParam(value = "greensClassId") String greensClassId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //是否登录状态
    returnMessages=checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    //判断
    if (!StringUtils.isNotEmpty(greensClassId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"分类编号不能为空！",null);
      return returnMessages;
    }
    GreensClass greensClass = greensClassService.findByGreensClassIdAndDelFalse(greensClassId);
    if (greensClass==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱分类不存在！",null);
      return returnMessages;
    }
    boolean flag =greensClassService.physicallyDeleteByGreensClassId(greensClassId);
    if (flag){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",greensClass);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",greensClass);
      return returnMessages;
    }
  }




}
