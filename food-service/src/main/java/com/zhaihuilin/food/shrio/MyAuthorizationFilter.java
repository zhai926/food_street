package com.zhaihuilin.food.shrio;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义权限过滤器
 */
public class MyAuthorizationFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthorizationFilter.class);

    /**
     * 表示是否允许访问 ，如果允许访问返回true，否则false；
     *
     * @param servletRequest
     * @param servletResponse
     * @param o               表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        /*Subject subject = getSubject(servletRequest, servletResponse);
        String url = getPathWithinApplication(servletRequest);
        logger.debug("当前用户正在访问的 url => " + url);
        //如果认证未通过或者是退出登录那么会定位到/rulai/unauth，此时拦截器放行（前面会经过认证过滤器FormAuthenticationFilter）
        if(Objects.equals("/rulai/unauth",url)){
            return true;
        }
        //进行权限认证
        return subject.isPermitted(url);*/
        //暂时不做权限认证，直接返回true
        return true;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //当 isAccessAllowed 返回 false 的时候，才会执行 method onAccessDenied,并进行后续处理
        logger.debug("当 isAccessAllowed 返回 false 的时候，才会执行 method onAccessDenied ");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 请求被拦截后直接返回json格式的响应数据
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        JSONObject res = new JSONObject();
        res.put("code", 500);//和前端约定好
        res.put("message", "没有权限");
        out = response.getWriter();
        out.append(res.toString());
        //返回 false 表示已经处理，例如页面跳转啥的，表示不在走以下的拦截器了（如果还有配置的话）
        return false;
    }
}
