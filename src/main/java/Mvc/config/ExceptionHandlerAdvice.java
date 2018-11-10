package Mvc.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//声明一个控制器建言
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)//定义全局处理，拦截所有的Exception类型
    public ModelAndView exception(Exception exception, WebRequest request)
    {
        ModelAndView modle=new ModelAndView("home");//跳转的页面
        modle.addObject("errorMessage",exception.getMessage());//添加参数
        return  modle;
    }
    @ModelAttribute//将注解添加到全局，所有注解的@RequestMapping的方法都可以得到值
    public void addAttributes(Model model){
        model.addAttribute("msg","123");
        model.addAttribute(model);
    }
    @InitBinder//定制web data binder
    public  void Init(WebDataBinder webdatabinder){
        webdatabinder.setDisallowedFields("id");//忽略request 的id
    }
}
