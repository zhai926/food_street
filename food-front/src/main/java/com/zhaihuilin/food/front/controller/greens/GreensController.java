package com.zhaihuilin.food.front.controller.greens;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhaihuilin.food.code.common.*;
import com.zhaihuilin.food.code.constant.StateConstant;
import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.greens.GreensClass;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.code.utils.StringUtils;
import com.zhaihuilin.food.front.config.MyJedisUtils;
import com.zhaihuilin.food.front.utils.CheckUserUtil;
import com.zhaihuilin.food.greens.GreensClassService;
import com.zhaihuilin.food.greens.GreensService;
import com.zhaihuilin.food.service.member.MemberService;
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
 * 菜谱视图层
 * Created by zhaihuilin on 2019/1/4 10:09.
 */
@RestController
@RequestMapping(value = "/api/greens")
@Log4j
public class GreensController {

     @Autowired
     private GreensService greensService;

     @Autowired
     private GreensClassService greensClassService;

     @Autowired
     private MemberService memberService;

     @Autowired
     private CheckUserUtil checkUserUtil;

     @Autowired
     private MyJedisUtils jedisUtils;


     /**
      * 保存菜谱消息   【】
      * @param greensName    菜谱名称
      * @param foodMaterials   所需食材
      * @param pics   菜谱的封面
      * @param foodSteps  菜谱的做法步骤
      * @param memberId   所属用户编号
      * @param greensClassId  所属的菜谱分类编号
      * @param greensIntro  菜谱简介
      * @param greensBody   温馨小提示
      * @param state   菜谱状态    为空的时候默认  不为空的时候就是草稿箱状态
      * @param request
      * @return
      */
     @PostMapping(value = "/savegreens")
     public ReturnMessages saveGreens(
             @RequestParam(value = "greensName") String greensName,
             @RequestParam(name = "foodMaterials") String foodMaterials,
             @RequestParam(name = "pics") String pics,
             @RequestParam(name = "foodSteps") String foodSteps,
             @RequestParam(name = "memberId") String memberId,
             @RequestParam(name = "greensClassId") String greensClassId,
             @RequestParam(name = "greensIntro",required = false) String greensIntro,
             @RequestParam(name = "greensBody",required = false) String greensBody,
             @RequestParam(name = "state",required = false) String state,
             HttpServletRequest request
     ){
          Greens greens= new Greens();
          ReturnMessages returnMessages = null;
          //是否登录状态
          returnMessages=checkUserUtil.returnMessages();
          if (returnMessages!=null){
               return  returnMessages;
          }
          //判断
          if (!StringUtils.isNotEmpty(greensName)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱名称不能为空！",null);
               return returnMessages;
          }
          greens.setGreensName(greensName);
          //食材
          if (!StringUtils.isNotEmpty(foodMaterials)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"所需食材不能为空！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(foodMaterials)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<FoodMaterial>>() {}.getType();
               try {
                    List<FoodMaterial> foodMaterialList = gson.fromJson(foodMaterials, type);
                    greens.setFoodMaterialList(foodMaterialList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "食材请求参数有误！", null);
               }
          }

          //菜谱封面
          if (!StringUtils.isNotEmpty(pics)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱封面不能为空！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(pics)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<Image>>() {}.getType();
               try {
                    List<Image> imageList = gson.fromJson(pics, type);
                    greens.setGreensPic(imageList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "封面请求参数有误！", null);
               }
          }
          // 所需步骤
          if (!StringUtils.isNotEmpty(foodSteps)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱步骤不能为空！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(foodSteps)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<FoodStep>>() {}.getType();
               try {
                    List<FoodStep>  foodStepList= gson.fromJson(foodSteps, type);
                    greens.setFoodStepList(foodStepList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "做法步骤请求参数有误！", null);
               }
          }
          if (!StringUtils.isNotEmpty(memberId)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"所属用户不能为空！",null);
               return returnMessages;
          }
          if (!StringUtils.isNotEmpty(greensClassId)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"分类编号不能为空！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(memberId)){
              Member member = memberService.findMemberByMemberId(memberId);
              if (member!=null){
                   greens.setMember(member);
              }else{
                   returnMessages = new ReturnMessages(RequestState.ERROR,"所属用户不存在！",null);
                   return returnMessages;
              }
          }
          if (StringUtils.isNotEmpty(greensClassId)){
               GreensClass GreensClass=greensClassService.findByGreensClassIdAndDelFalse(greensClassId);
               if (GreensClass!= null){
                    greens.setGreensClass(GreensClass);
               }else{
                    returnMessages = new ReturnMessages(RequestState.ERROR,"分类不存在！",null);
                    return returnMessages;
               }
          }
          if (StringUtils.isNotEmpty(greensIntro)){
               greens.setGreensIntro(new StringBuffer(greensIntro));
          }
          if (StringUtils.isNotEmpty(greensBody)){
               greens.setGreensBody(new StringBuffer(greensBody));
          }
          if (StringUtils.isNotEmpty(state)){
               greens.setState(StateConstant.GREENS_STATE_HOUSE.toString());
          }
          //保存
          greens=greensService.saveGreens(greens);
          if (greens!=null){
               returnMessages = new ReturnMessages(RequestState.SUCCESS,"新增成功！",greens);
               return returnMessages;
          }else{
               returnMessages = new ReturnMessages(RequestState.ERROR,"新增失败！",null);
               return returnMessages;
          }
     }


     /**
      * 编辑菜谱消息
      * @param greensId   菜谱编号
      * @param greensName    菜谱名称
      * @param foodMaterials   所需食材
      * @param pics   菜谱的封面
      * @param foodSteps  菜谱的做法步骤
      * @param greensClassId  所属的菜谱分类编号
      * @param greensIntro  菜谱简介
      * @param greensBody   温馨小提示
      * @param request
      * @return
      */
     @PostMapping(value = "/updategreens")
     public ReturnMessages updateGreens(
             @RequestParam(value = "greensId") String greensId,
             @RequestParam(value = "greensName",required = false) String greensName,
             @RequestParam(name = "foodMaterials",required = false) String foodMaterials,
             @RequestParam(name = "pics",required = false) String pics,
             @RequestParam(name = "foodSteps",required = false) String foodSteps,
             @RequestParam(name = "greensClassId",required = false) String greensClassId,
             @RequestParam(name = "greensIntro",required = false) String greensIntro,
             @RequestParam(name = "greensBody",required = false) String greensBody,
             HttpServletRequest request
     ){
          ReturnMessages returnMessages = null;
          //是否登录状态
          returnMessages=checkUserUtil.returnMessages();
          if (returnMessages!=null){
               return  returnMessages;
          }
          //判断
          if (!StringUtils.isNotEmpty(greensId)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱编号不能为空！",null);
               return returnMessages;
          }
          Greens greens = greensService.findByGreensIdAndDelFalse(greensId);
          if (greens == null){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱不存在！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(greensName)){
               greens.setGreensName(greensName);
          }
          //食材
          if (StringUtils.isNotEmpty(foodMaterials)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<FoodMaterial>>() {}.getType();
               try {
                    List<FoodMaterial> foodMaterialList = gson.fromJson(foodMaterials, type);
                    greens.setFoodMaterialList(foodMaterialList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "食材请求参数有误！", null);
               }
          }
          //菜谱封面
          if (StringUtils.isNotEmpty(pics)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<Image>>() {}.getType();
               try {
                    List<Image> imageList = gson.fromJson(pics, type);
                    greens.setGreensPic(imageList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "封面请求参数有误！", null);
               }
          }
          // 所需步骤
          if (StringUtils.isNotEmpty(foodSteps)){
               Gson gson = new Gson();
               Type type = new TypeToken<ArrayList<FoodStep>>() {}.getType();
               try {
                    List<FoodStep>  foodStepList= gson.fromJson(foodSteps, type);
                    greens.setFoodStepList(foodStepList);
               } catch (Exception e) {
                    return new ReturnMessages(RequestState.ERROR, "做法步骤请求参数有误！", null);
               }
          }
          if (StringUtils.isNotEmpty(greensClassId)){
               GreensClass GreensClass=greensClassService.findByGreensClassIdAndDelFalse(greensClassId);
               if (GreensClass!= null){
                    greens.setGreensClass(GreensClass);
               }else{
                    returnMessages = new ReturnMessages(RequestState.ERROR,"分类不存在！",null);
                    return returnMessages;
               }
          }
          if (StringUtils.isNotEmpty(greensIntro)){
               greens.setGreensIntro(new StringBuffer(greensIntro));
          }
          if (StringUtils.isNotEmpty(greensBody)){
               greens.setGreensBody(new StringBuffer(greensBody));
          }
          //编辑的时候 要更改其状态
          greens.setState(StateConstant.GREENS_STATE_ON_CHECKING.toString());
          //保存
          greens=greensService.updateGreens(greens);
          if (greens!=null){
               returnMessages = new ReturnMessages(RequestState.SUCCESS,"编辑成功！",greens);
               return returnMessages;
          }else{
               returnMessages = new ReturnMessages(RequestState.ERROR,"编辑失败！",null);
               return returnMessages;
          }
     }


     /**
      * 获取用户自己的菜谱
      * @param state    状态  【当前台点击草稿箱的时候 将 state 设置有值 】
      * @param request
      * @return
      */
     @PostMapping("/findMeGreens")
     public ReturnMessages findMeGreens(
         @RequestParam(name = "state",required = false) String state,
         HttpServletRequest request
     ){
          ReturnMessages returnMessages = null;
          //是否登录状态
          returnMessages=checkUserUtil.returnMessages();
          if (returnMessages!=null){
               return  returnMessages;
          }
          //获取当前登录的用户
          Member member = checkUserUtil.getMember();
          if (member == null){
               returnMessages = new ReturnMessages(RequestState.ERROR,"用户不存在！",null);
               return returnMessages;
          }
          if (StringUtils.isNotEmpty(state)){
               state = StateConstant.GREENS_STATE_HOUSE.toString();
          }
          List<Greens> greensList = greensService.findByMemberAndDelFalseAndState(member,state);
          if (greensList!=null && greensList.size()>0){
               returnMessages = new ReturnMessages(RequestState.SUCCESS,"获取成功！",greensList);
               return returnMessages;
          }else {
               returnMessages = new ReturnMessages(RequestState.ERROR,"获取失败！",null);
               return returnMessages;
          }
     }


     /**
      * 条件查询
      * @param greensId        菜谱编号
      * @param greensName     菜谱名称
      * @param state   状态
      * @param greensClassName  分类名称
      * @param username  用户名
      * @param request
      * @return
      */
     @PostMapping(value = "/findAllGreens")
     public  ReturnMessages  findAllGreens(
             @RequestParam(value = "greensId",required = false) String greensId,
             @RequestParam(value = "greensName",required = false) String greensName,
             @RequestParam(value = "state",required = false) String state,
             @RequestParam(value = "greensClassName",required = false) String greensClassName,
             @RequestParam(value = "username",required = false) String username,
             @RequestParam(name = "page" ,required = false,defaultValue = "0") String page,
             @RequestParam(name = "pageSize" ,required = false,defaultValue = "5") String pageSize,
             HttpServletRequest request
     ){
          ReturnMessages returnMessages = null;
          Greens greens = new Greens();
          GreensClass greensClass = new GreensClass();
          Member member = new Member();
          if (StringUtils.isNotEmpty(greensId)){
               greens.setGreensId(greensId);
          }
          if (StringUtils.isNotEmpty(greensId)){
               greens.setGreensName(greensName);
          }
          if (StringUtils.isNotEmpty(state)){
               greens.setState(state);
          }
          if (StringUtils.isNotEmpty(greensClassName)){
               greensClass =  greensClassService.findByGreensClassNameAndDelFalse(greensClassName);
          }
          if (StringUtils.isNotEmpty(username)){
               member = memberService.findMemberByUsername(username);
          }
          Sort sort = new Sort(Sort.Direction.DESC,"createTime"); //排序
          Pageable pageable=new PageRequest(Integer.parseInt(page),Integer.parseInt(pageSize),sort);
          Page<Greens> greensPage = greensService.findGreens(greens,member,greensClass,pageable);
          if (greensPage !=null && greensPage.getContent().size()>0){
               return  new ReturnMessages(RequestState.SUCCESS,"获取成功！",greensPage);
          }else {
               return  new ReturnMessages(RequestState.ERROR,"获取失败！",null);
          }
     }


     /**
      * 逻辑删除
      * @param greensId  菜谱编号
      * @return
      */
     @PostMapping(value = "/deleteGreens")
     public ReturnMessages deleteGreens(
             @RequestParam(value = "greensId",required = false) String greensId

     ){
          ReturnMessages returnMessages = null;
          //是否登录状态
          returnMessages=checkUserUtil.returnMessages();
          if (returnMessages!=null){
               return  returnMessages;
          }
          //获取当前用户信息
          Member member = checkUserUtil.getMember();
          if (!StringUtils.isNotEmpty(greensId)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱编号不能为空！",null);
               return returnMessages;
          }
          Greens greens = greensService.findByGreensIdAndDelFalse(greensId);
          if (greens==null){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱不存在！",null);
               return returnMessages;
          }
          // 用户只能删除自己创建的菜谱
          Member greenstomember = greens.getMember(); //获取此菜单所创建的用户
          if (member.getMemberId() .equals(greenstomember.getMemberId())){
               returnMessages = new ReturnMessages(RequestState.ERROR,"用户只能删除自己创建的菜谱！",null);
               return returnMessages;
          }
          boolean flag =greensService.deleteGreensByGreenId(greensId);
          if (flag){
               returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",greens);
               return returnMessages;
          }else{
               returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",null);
               return returnMessages;
          }
     }

     /**
      * 物理删除
      * @param greensId  菜谱编号
      * @return
      */
     @PostMapping(value = "/physicallyDeleteGreens")
     public ReturnMessages physicallyDeleteGreens(
             @RequestParam(value = "greensId",required = false) String greensId

     ){
          ReturnMessages returnMessages = null;
          //是否登录状态
          returnMessages=checkUserUtil.returnMessages();
          if (returnMessages!=null){
               return  returnMessages;
          }
          //获取当前用户信息
          Member member = checkUserUtil.getMember();
          if (!StringUtils.isNotEmpty(greensId)){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱编号不能为空！",null);
               return returnMessages;
          }
          Greens greens = greensService.findByGreensIdAndDelFalse(greensId);
          if (greens==null){
               returnMessages = new ReturnMessages(RequestState.ERROR,"菜谱不存在！",null);
               return returnMessages;
          }
          // 用户只能删除自己创建的菜谱
          Member greenstomember = greens.getMember(); //获取此菜单所创建的用户
          if (member.getMemberId() .equals(greenstomember.getMemberId())){
               returnMessages = new ReturnMessages(RequestState.ERROR,"用户只能删除自己创建的菜谱！",null);
               return returnMessages;
          }
          boolean flag =greensService.physicallyDeleteByGreenId(greensId);
          if (flag){
               returnMessages = new ReturnMessages(RequestState.SUCCESS,"删除成功！",greens);
               return returnMessages;
          }else{
               returnMessages = new ReturnMessages(RequestState.ERROR,"删除失败！",null);
               return returnMessages;
          }
     }

     /**
      * 根据编号进行查询
      * @param greensId
      * @return
      */
     @PostMapping(value = "/findGreensById")
     public ReturnMessages findGreensById(
             @RequestParam(value = "greensId",required = false) String greensId

     ){
          ReturnMessages returnMessages = null;
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
          try {
               Greens greens = greensService.findByGreensIdAndDelFalse(greensId);
               String  key = "greens-"+ member.getMemberId() + "-" + greens.getGreensId();
               if (greens!=null){
                    //将浏览过的菜谱存储
                    jedisUtils.set(key,greens.getGreensId(),10);
                    returnMessages = new ReturnMessages(RequestState.SUCCESS,"查询成功！",greens);
                    return returnMessages;
               }else{
                    returnMessages = new ReturnMessages(RequestState.ERROR,"查询失败！",null);
                    return returnMessages;
               }
          }catch (Exception e){
               log.info("失败的原因是:" + e.getMessage());
               returnMessages = new ReturnMessages(RequestState.ERROR,"查询失败！",null);
               return returnMessages;
          }
     }
}
