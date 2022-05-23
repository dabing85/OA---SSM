package com.dabing.oa.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录拦截器，如果你没有登录就拦截你，所有方法写在登录前;写完之后要到sping.xml里配置才能生效的
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //有些路径是可以不用登录不拦截的
        String url=request.getRequestURI();
        if(url.toLowerCase().indexOf("login")>=0){
            return true;//去登录的url不拦截，直接放行,toLowerCase()是把字母全部转换成小写
        }
        HttpSession session=request.getSession();
        if(session.getAttribute("employee")!=null){
            return true;//session不为空，证明已经登录过了
        }
        response.sendRedirect("/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
