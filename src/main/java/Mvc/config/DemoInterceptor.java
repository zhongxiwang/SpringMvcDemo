package Mvc.config;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
public class DemoInterceptor extends HandlerInterceptorAdapter {
    @Override//执行前
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        long startTime=System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }


    @Override//执行完成后的拦截
    public  void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelandVeiw){
        long starttime=(Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime=System.currentTimeMillis();
        System.out.println("本次请求处理事件"+new Long(endTime=-starttime));
        request.setAttribute("ahndlingTime",endTime-starttime);
    }
}
