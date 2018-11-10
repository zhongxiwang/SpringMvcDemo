package Mvc.config;

import Mvc.Model.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

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
