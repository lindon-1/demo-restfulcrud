package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
    @RequestMapping("/success")
    public String success(Map<String,Object> map){


        map.put("hello","你好");
        map.put("users", Arrays.asList("lindon","weichen","hongsheng"));
        return "success";
    }
    /*@RequestMapping({"/","/index.html"})
    public String index(){
        return "login";
    }*/
}
