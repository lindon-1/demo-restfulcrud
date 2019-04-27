package com.example.demo.Controller;

import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class loginController {
    @Resource
    private UserService userService;

    @PostMapping(value="/user/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Map<String,Object> map,HttpSession session){
        if(StringUtils.isEmpty(username))
            return "login";
        User user = userService.findByUserName(username);
        if(user == null){
            map.put("msg","用户名或密码错误!");
            return "login";
        }
        System.out.println(user.toString());
        if(user.getUsername().equals(username) && user.getPassword().equals(password)){
            //防止表单重复提交，可重定向到主页
            session.setAttribute("loginuser",username);
            return "redirect:/main.html";
        }else{
            map.put("msg","用户名或密码错误!");
            return "login";
        }
    }
    @RequestMapping("/user/loginout")
    public String loginout(HttpSession session){
        String loginuser = (String)session.getAttribute("loginuser");
        System.out.println(loginuser);
        return "login";
    }
    @RequestMapping("/users")
    public String userManger(HttpSession session, Model model){
        String loginuser = (String) session.getAttribute("loginuser");
        User user = userService.findByUserName(loginuser);
        if(user != null && "超级管理员".equals(user.getUserType())){
            List<User> users = userService.findAll();
            model.addAttribute("users",users);
            return "user/list";
        }else {
            return "success";
        }
    }
    @PostMapping("user")
    public String userAdd(User user){
        user.setUserType("管理员");
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/checkUser")
    @ResponseBody
    public Map<String,Boolean> checkUser(@RequestParam("username") String username,@RequestParam("password") String password){
        Boolean isHead = userService.checkUser(username);
        Map<String,Boolean> map = new HashMap();
        map.put("isHead",isHead);
        return map;
    }

    @GetMapping("/user/{id}")
    public String updateView(@PathVariable("id") Integer id, Model model){
        User user = userService.selectById(id);
        model.addAttribute("user",user);
        return "/user/update";
    }
    @GetMapping("/user")
    public String addView(){
        return "/user/add";
    }

    @PutMapping("/user")
    public String update(User user){
        userService.update(user);
        return "redirect:/users";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") Integer id){
        if(id >1){
            userService.delete(id);
        }
        return "redirect:/users";
    }
}
