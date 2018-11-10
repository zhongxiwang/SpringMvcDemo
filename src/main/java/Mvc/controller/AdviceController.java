package Mvc.controller;

import Mvc.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdviceController {
    @RequestMapping("/advice")
    public  String getSomething(@ModelAttribute("msg") String msg, User user){
            throw new IllegalArgumentException("sorry."+msg);
    }
    @RequestMapping(value = "/convert",produces = {"application/x-wisely"})
    public @ResponseBody User convert(@RequestBody User us) {
        return us;
    }
}
