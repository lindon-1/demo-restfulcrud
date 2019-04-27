package com.example.demo.Controller;

import com.example.demo.entities.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private EmpService empService;
    @GetMapping("/departs")
    public String list(Model model) {
        List<Department> departments = departmentService.selectAll();
       // departments.stream().forEach(e -> e.toString());
        model.addAttribute("departments",departments);
        return "depart/list";
    }
    @PostMapping("/depart")
    public String add(Department department){
        departmentService.add(department);
        return "redirect:/departs";
    }
    @DeleteMapping("/depart/{departmentId}")
    public String delete(@PathVariable("departmentId") String departmentId){
        System.out.println("id wei : " + departmentId);
        Department department = departmentService.selectByDepartmentId(departmentId);
        if(department != null){
            Integer id = department.getId();
            System.out.println(id);
            empService.deleteByBmId(id);
        }
        departmentService.delete(departmentId);
        return "redirect:/departs";
    }
    @PutMapping("/depart")
    public String update(Department department){
        departmentService.update(department);
        return "redirect:/departs";
    }
    @GetMapping("/depart/{departmentId}")
    public String updateView(@PathVariable("departmentId") String departmentId, Model model){
        Department department = departmentService.selectByDepartmentId(departmentId);
        model.addAttribute("department",department);
        return "depart/add";
    }
    @RequestMapping("/depart/count")
    @ResponseBody
    public Map<String, List> getCount(){
        return departmentService.getCount();
    }

    @GetMapping("/checkDepart")
    @ResponseBody
    public Map<String,Boolean> checkDepart(@RequestParam("departmentId") String departmentId,@RequestParam("departmentName") String departmentName){
        Boolean isHead = departmentService.checkDepart(departmentId, departmentName);
        Map<String,Boolean> map = new HashMap();
        map.put("isHead",isHead);
        return map;
    }
}
