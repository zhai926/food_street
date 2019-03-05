package com.zhaihuilin.food.shrio;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class MyExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (ex instanceof UnauthenticatedException) {
            attributes.put("code", ShiroEnum.UNAUTHORIZTION.getCode());
            attributes.put("msg", ShiroEnum.UNAUTHORIZTION.getDesc());
        } else if (ex instanceof UnauthorizedException) {
            attributes.put("code", ShiroEnum.UNAUTHENTICATION.getCode());
            attributes.put("msg", ShiroEnum.UNAUTHENTICATION.getDesc());
        }
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
