package com.example.demo.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandleIntecptor implements HandlerInterceptor {
    @Override
    //执行目标前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       Object user=request.getSession().getAttribute("loginuser");
       if(user==null){
           //为登录，返回登录页面
           request.setAttribute("msg","没有权限！");
           request.getRequestDispatcher("/index.html").forward(request,response);
           return false;
       }else{
           return true;
       }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
