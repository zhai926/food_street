package com.zhaihuilin.food.admin.controller.hotactivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhaihuilin.food.admin.utils.CheckUserUtil;
import com.zhaihuilin.food.code.common.Image;
import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.hotactivity.HotActivity;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.hotactivity.HotActivityService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaihuilin on 2019/1/8 13:55.
 */
@RestController
@RequestMapping(value = "/api/hot")
@Log4j
public class HotActivityController {

  @Autowired
  private HotActivityService hotActivityService;

  @Autowired
  private CheckUserUtil checkUserUtil;

  /**
   * 新增热门信息
   * @param hotActivitytitle  标题
   * @param pics   封面
   * @param content   内容
   * @param request
   * @return
   */
  @PostMapping(value = "/savehot")
  public ReturnMessages saveHotActivity(
    @RequestParam(name = "hotActivitytitle") String hotActivitytitle,
    @RequestParam(name = "pics") String pics,
    @RequestParam(name = "content") String content,
    HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    HotActivity hotActivity = new HotActivity();
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
       return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(hotActivitytitle)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"标题不能为空！",null);
      return returnMessages;
    }
    hotActivity.setHotActivitytitle(hotActivitytitle);
    //活动封面
    if (!StringUtils.isNotEmpty(pics)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"封面不能为空！",null);
      return returnMessages;
    }
    if (StringUtils.isNotEmpty(pics)){
      Gson gson = new Gson();
      Type type = new TypeToken<ArrayList<Image>>() {}.getType();
      try {
        List<Image> imageList = gson.fromJson(pics, type);
        hotActivity.setGreensPic(imageList);
      } catch (Exception e) {
        return new ReturnMessages(RequestState.ERROR, "封面请求参数有误！", null);
      }
    }
    if (!StringUtils.isNotEmpty(content)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"内容不能为空！",null);
      return returnMessages;
    }
    hotActivity.setContent(new StringBuffer(content));
    //保存
    hotActivity =hotActivityService.saveHotActivity(hotActivity);
    if (hotActivity!=null){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"新增成功！",hotActivity);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"新增失败！",null);
      return returnMessages;
    }
  }


  /**
   * 编辑热门信息
   * @param hotActId   编号
   * @param hotActivitytitle  标题
   * @param pics   封面
   * @param content   内容
   * @param  state 状态
   * @param request
   * @return
   */
  @PostMapping(value = "/updatehot")
  public ReturnMessages updateHotActivity(
          @RequestParam(name = "hotActId") String hotActId,
          @RequestParam(name = "hotActivitytitle",required = false) String hotActivitytitle,
          @RequestParam(name = "pics",required = false) String pics,
          @RequestParam(name = "content",required = false) String content,
          @RequestParam(name = "state",required = false) String state,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(hotActId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编号不能为空！",null);
      return returnMessages;
    }
    HotActivity hotActivity = hotActivityService.findByHotActIdAndDelFalse(hotActId);
    if (hotActivity == null){
       returnMessages = new ReturnMessages(RequestState.ERROR,"该热门活动不存在！",null);
       return returnMessages;
    }
    if (StringUtils.isNotEmpty(hotActivitytitle)){
      hotActivity.setHotActivitytitle(hotActivitytitle);
    }
    //活动封面
    if (StringUtils.isNotEmpty(pics)){
      Gson gson = new Gson();
      Type type = new TypeToken<ArrayList<Image>>() {}.getType();
      try {
        List<Image> imageList = gson.fromJson(pics, type);
        hotActivity.setGreensPic(imageList);
      } catch (Exception e) {
        return new ReturnMessages(RequestState.ERROR, "封面请求参数有误！", null);
      }
    }
    if (StringUtils.isNotEmpty(content)){
      hotActivity.setContent(new StringBuffer(content));
    }
    if (StringUtils.isNotEmpty(state)){
      hotActivity.setState(state);
    }
    //保存
    hotActivity =hotActivityService.updateHotActivity(hotActivity);
    if (hotActivity!=null){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"编辑成功！",hotActivity);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"编辑失败！",null);
      return returnMessages;
    }
  }

  /**
   * 根据编号进行查询
   * @param hotActId  编号
   * @param request
   * @return
   */
  @PostMapping(value = "/findHotById")
  public ReturnMessages findHotActivityById(
          @RequestParam(name = "hotActId") String hotActId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(hotActId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编号不能为空！",null);
      return returnMessages;
    }
    HotActivity hotActivity = hotActivityService.findByHotActIdAndDelFalse(hotActId);
    if (hotActivity!=null){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"该热门活动存在！",hotActivity);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"该热门活动不存在！",null);
      return returnMessages;
    }
  }

  /**
   * 分页条件查询
   * @param hotActId   编号
   * @param hotActivitytitle  标题
   * @param state   状态
   * @param startTime  开始时间
   * @param endTime   介绍时间
   * @param page  第几页
   * @param pageSize 每页显示的条数
   * @param request
   * @return
   */
  @PostMapping(value = "/findAll")
  public ReturnMessages findAll(
          @RequestParam(name = "hotActId", required = false) String hotActId,
          @RequestParam(name = "hotActivitytitle",required = false) String hotActivitytitle,
          @RequestParam(name = "state",required = false) String state,
          @RequestParam(name = "startTime", required = false) String startTime,
          @RequestParam(name = "endTime", required = false) String endTime,
          @RequestParam(name = "page" ,required = false,defaultValue = "0") String page,
          @RequestParam(name = "pageSize" ,required = false,defaultValue = "10") String pageSize,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    Long startDate=null;
    Long endDate =null;
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    Sort sort = new Sort(Sort.Direction.DESC,"createTime"); //排序
    Pageable pageable=new PageRequest(Integer.parseInt(page),Integer.parseInt(pageSize),sort);
    HotActivity hotActivity = new HotActivity();
    if (StringUtils.isNotEmpty(hotActId)){
       hotActivity.setHotActId(hotActivitytitle);
    }
    if (StringUtils.isNotEmpty(hotActivitytitle)){
      hotActivity.setHotActivitytitle(hotActivitytitle);
    }
    if (StringUtils.isNotEmpty(state)){
      hotActivity.setState(state);
    }
    try {
      if (StringUtils.isNotEmpty(startTime)  ){
        startDate=Long.valueOf(startTime);
      }
      if (StringUtils.isNotEmpty(endTime)){
        endDate=Long.valueOf(endTime);
      }
      if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime) &&  Long.valueOf(startTime) >Long.valueOf(endTime)){
        returnMessages= new ReturnMessages(RequestState.ERROR,"暂无数据",null);
        return returnMessages;
      }
    }catch (Exception e){
      log.info("时间参数错误:"+e.getMessage());
      return  new ReturnMessages(RequestState.ERROR,"时间参数错误",null);
    }
    Page<HotActivity> hotActivityPage= hotActivityService.findAll(hotActivity,startDate,endDate,pageable);
    if (hotActivityPage!=null && hotActivityPage.getContent().size()>0){
      returnMessages= new ReturnMessages(RequestState.SUCCESS,"查询成功！",hotActivityPage);
      return returnMessages;
    }else{
      returnMessages= new ReturnMessages(RequestState.ERROR,"暂无数据",null);
      return returnMessages;
    }
  }


  /**
   * 逻辑删除
   * @param hotActId  编号
   * @param request
   * @return
   */
  @PostMapping(value = "/deleteHotById")
  public ReturnMessages deleteHotActivityById(
          @RequestParam(name = "hotActId") String hotActId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(hotActId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编号不能为空！",null);
      return returnMessages;
    }
    HotActivity hotActivity = hotActivityService.findByHotActIdAndDelFalse(hotActId);
    if (hotActivity==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"该热门活动不存在！",hotActivity);
      return returnMessages;
    }
    boolean flag=hotActivityService.deleteById(hotActId);
    if (flag){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",hotActivity);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",null);
      return returnMessages;
    }
  }


  /**
   * 物理删除
   * @param hotActId  编号
   * @param request
   * @return
   */
  @PostMapping(value = "/physicallyDeleteHotById")
  public ReturnMessages physicallyDeleteHotActivityById(
          @RequestParam(name = "hotActId") String hotActId,
          HttpServletRequest request
  ){
    ReturnMessages returnMessages = null;
    //判断是否登录
    returnMessages = checkUserUtil.returnMessages();
    if (returnMessages!=null){
      return  returnMessages;
    }
    if (!StringUtils.isNotEmpty(hotActId)){
      returnMessages = new ReturnMessages(RequestState.ERROR,"编号不能为空！",null);
      return returnMessages;
    }
    HotActivity hotActivity = hotActivityService.findByHotActIdAndDelFalse(hotActId);
    if (hotActivity==null){
      returnMessages = new ReturnMessages(RequestState.ERROR,"该热门活动不存在！",hotActivity);
      return returnMessages;
    }
    boolean flag=hotActivityService.physicallyDeleteByHotActId(hotActId);
    if (flag){
      returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",hotActivity);
      return returnMessages;
    }else{
      returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",null);
      return returnMessages;
    }
  }



}
