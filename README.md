# SpringMvcDemo
学习Spring Mvc的一个Demo示例


## 核心说明
### WebApplicationlnitializer
Spring MVC提供了一个Dispatcherservlet来开发Web应用。在Servlet 2.5及以下的时候只要在web.xml下配置〈servlet〉元素即可。但我们在本节将使用Servlet 3.0+无web.xml的配置方式，在Spring MVC里实现WebApplicationlnitializer接口便可实现等同于web.xml的配置。
### ViewResolver
Spring MVC的ViewResolver，这是Spring MVC视图(JSP下就是html)渲染的核心机制Spring MVC里有一个接口叫做ViewResolver(我们的ViewResolver都实现该接口)，实现这个接口要重写方法resolveviewNameo，这个方法的返回值是接口View,而View的职责就是使用model、request、response对象，并将渲染的视图(不一定是html,可能是json、xml、pdD返回给浏览器。

### &#64;ControllerAdvice
通过回&#64;ControllerAdvice，我们可以将对于控制器的全局配置放置在同一个位置，注解了Controller的类的方法可使用&#64;ExceptionHandler&#64;InitBinder&#64;Mode1Attribute注解到方法，这对所有注解了&#64;RequestMapping的控制器内的方法有效。
&#64;ExceptionHandler用于全局处理控制器里的异常。

###HttpMessageConverter
HttpMessageConverter是用来处理request和respolue里的数据的。Spring为我们内置了大量的 HttpMessageconverter, 例如, MappingJackson2HttpMessageconverter. StringHttpMessageConverter等。

###拦截器
继承自HandlerInterceptorAdapter

##代码示例

WebApplicationInitializer接口

```java

//web 配置
public class webinit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext cont=new AnnotationConfigWebApplicationContext();
        cont.register(MvcConfiguration.class);
        cont.setServletContext(servletContext);
    }
}
```

重写WebMvcConfigurerAdapter，配置mvc
```java


@Configuration
@ComponentScan(basePackages="Mvc")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	///静态资源配置
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	///添加自定义 HttpMessageConvert，并注册这个HttpMessageconverter到 Spring MVC.
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
	{
			converters.add(Convert());
	}
	///配置再addViewController中添加ViewController映射页面
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/test").setViewName("/index");
	}

	@Bean
	public HttpMessageConverter Convert(){
		return new Mvc.config.HttpMessageConverter();
	}

}

```

创建自定义HttpMessageConverter
```java

///继承AbstractHttpMessageConverter来实现自定义的HttpMessageConverter
public class HttpMessageConverter extends AbstractHttpMessageConverter<User> {

    public HttpMessageConverter(){
        //自定义一个媒体类型
        super(new MediaType("application","x-wisely", Charset.forName("UTF-8")));
    }


    ///只处理User类型的数据
    @Override
    protected boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    ///处理请求的数据
    @Override
    protected User readInternal(Class<? extends User> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp=StreamUtils.copyToString(httpInputMessage.getBody(),Charset.forName("UTF-8"));
        String[] list= temp.split(",");
        return new User(list[1],list[2]);
    }

    ///处理如何输出数据到response
    @Override
    protected void writeInternal(User user, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        String out="username,password";
        httpOutputMessage.getBody().write(out.getBytes());
    }

}

```

全局配置

```java


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

```
拦截器
```java
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
```

Controller示例

```java
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

```
