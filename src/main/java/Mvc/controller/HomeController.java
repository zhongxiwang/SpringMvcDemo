package Mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import Mvc.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class HomeController {

	@RequestMapping(value="/index")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");//打开页面home.jsp
	}

	@RequestMapping("/view")//打开页面home.jsp
	public  String hello(HttpServletResponse response){
		return  "home";
	}

	///这里默认路径是/test
	@RequestMapping(produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public  String demo(){
		return  "return text";
	}

	/// 多个路径映射
	@RequestMapping(value={"/demopath/{str}","/demopath2/{str}"},produces = "application/json;charset=UTF-8")
	public  @ResponseBody
	String demoPath(@PathVariable String str ){
		return  "return:"+str;
	}

	/// 多个路径映射
	@RequestMapping(value="/demo/{str}",produces = "application/json;charset=UTF-8")
	public  @ResponseBody
	String demoPath2(User str ){
		return  "return:"+str.getUsername();
	}
}
