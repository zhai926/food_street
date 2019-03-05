package com.zhaihuilin.food.front.controller.history;

import com.zhaihuilin.food.code.common.RequestState;
import com.zhaihuilin.food.code.common.ReturnMessages;
import com.zhaihuilin.food.code.entity.greens.Greens;
import com.zhaihuilin.food.code.entity.member.Member;
import com.zhaihuilin.food.front.config.MyJedisUtils;
import com.zhaihuilin.food.front.utils.CheckUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 浏览记录
 * Created by zhaihuilin on 2019/1/9 17:32.
 */
@RestController
@RequestMapping(value = "/api/myhistory")
public class HistoryController {
    @Autowired
    private MyJedisUtils jedisUtils;

    @Autowired
    private CheckUserUtil checkUserUtil;

    /**
     * 获取历史记录
     * @return
     */
     @PostMapping(value = "/list")
     public ReturnMessages  getList(){
       ReturnMessages returnMessages = null;
       //判断用户是否登录
       returnMessages=checkUserUtil.returnMessages();
       if (returnMessages!=null){
         return returnMessages;
       }
       //获取当前登录的用户
       Member member  =checkUserUtil.getMember();
       String key = "greens-"  + member.getMemberId();
       List<Greens>  greensList =jedisUtils.get(key);
       if (greensList!=null && greensList.size()>0){
         returnMessages = new ReturnMessages(RequestState.SUCCESS,"查询成功！",greensList);
         return returnMessages;
       }else {
         returnMessages = new ReturnMessages(RequestState.ERROR,"暂无痕迹！",null);
         return returnMessages;
       }
     }

}
