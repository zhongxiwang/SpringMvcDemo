package Mvc.controller;

import Mvc.Model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ApiController {
    @RequestMapping(value = "/getjson/{str}",produces = {"application/json;charset=UTF-8"})
    public User GetJson(@PathVariable String Str){
        User use=new User();
        use.setPassword("root");
        use.setUsername("roots-");
        return use;
    }

    @RequestMapping(value = "/getxml/{str}",produces = {"application/xml;charset=UTF-8"})
    public User Getxml(@PathVariable String Str){
        User use=new User();
        use.setPassword("root");
        use.setUsername("roots-");
        return use;
    }
}
