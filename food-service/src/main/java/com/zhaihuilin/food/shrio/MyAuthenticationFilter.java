package com.zhaihuilin.food.shrio;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义认证过滤器
 */
public class MyAuthenticationFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthorizationFilter.class);

    private static volatile MyAuthenticationFilter myAuthenticationFilter = null;

    private MyAuthenticationFilter(){}

    /**
     * 单例自定义认证拦截器
     * @return
     */
    public static MyAuthenticationFilter getMyAuthenticationFilter() {
        if (myAuthenticationFilter == null) {
            synchronized (MyAuthenticationFilter.class) {
                if (myAuthenticationFilter == null) {
                    myAuthenticationFilter = new MyAuthenticationFilter();
                }
            }
        }
        return myAuthenticationFilter;
    }

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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //过滤vue框架的预请求，不需要进行认证
        //if (Objects.equals(request.getMethod(),"OPTIONS")) {
        //    return true;
        //}
        Subject subject = getSubject(servletRequest, servletResponse);
        return subject.isAuthenticated();
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
        res.put("code", 998);//和前端约定好
        res.put("message", "未登录");
        out = response.getWriter();
        out.append(res.toString());
        //返回 false 表示已经处理，例如页面跳转啥的，表示不在走以下的拦截器了（如果还有配置的话）
        return false;
    }
}
