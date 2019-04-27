package com.example.demo.Controller;


import com.example.demo.Dto.EmpDto;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {
//    @Autowired
//    EmployeeDao employeeDao;
//    @Autowired
//    DepartmentDao departmentDao;

    @Resource
    EmpService empService;
    @Resource
    DepartmentService departmentService;

    //查询所有员工
    @GetMapping("/emps")
    public String list(Model model, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize) {
        //模板引擎会自动拼串，pre:classpath:/templates/   suf:xxx.html
        List<Employee> employees = empService.selectAll();
        //分页处理
        Integer sizeNum = 10;
        //Integer page = Integer.parseInt(pageSize);
        Integer firstNum = (pageSize - 1) * sizeNum;
        Integer lastNum = firstNum + sizeNum;
        Integer empSize = employees.size();
        Integer endPage = empSize % sizeNum == 0 ? (empSize / sizeNum) : (empSize / sizeNum + 1);
        List<Employee> subList = employees.subList(firstNum, lastNum > empSize ? empSize : lastNum);

        List<EmpDto> empDtos = getEmpDtos(subList);
        Map<String, Object> map = new HashMap<>();
        if (lastNum < empSize) {
            map.put("nextPage", pageSize + 1);
        } else {
            map.put("nextPage", 1);
        }
        if (pageSize > 1) {
            map.put("prePage", pageSize - 1);
        } else {
            map.put("prePage", 1);
        }
        map.put("total",employees.size());
        map.put("page",pageSize);
        map.put("endPage", endPage);
        map.put("emps", empDtos);
        model.addAttribute("map", map);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toaddPage(Model model) {
//     Collection departments=   departmentDao.getDepartments();
        List<Department> departments = departmentService.selectAll();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //员工添加
    //springmvc自动将请求参数和入参对象一一绑定，要求请求参数和javabean入参的对象属性一致
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //System.out.println(employee);
//        employeeDao.save(employee);
        //如果没有设置入职时间，默认当前时间
        employee.setBirth(Optional.ofNullable(employee.getBirth()).orElse(new Date()));
        empService.add(employee);
        //redirect 重定向到一个地址
        //forward：转发到一个地址
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
//       Employee employee= employeeDao.get(id);
        Employee employee = empService.selectById(id);
        System.out.println(">>>>>>");
        System.out.println(employee.toString());
        model.addAttribute("emp", employee);
        //页面要显示所有部门
        //Collection departments=   departmentDao.getDepartments();
        List<Department> departments = departmentService.selectAll();
        model.addAttribute("depts", departments);
        //回到修改页面,修改页面跟添加页面是同一个页面
        return "/emp/add";
    }

    //修改
    @PutMapping("/emp")
    public String updateEmp(Employee employee) {
        System.out.println(employee);
        //employeeDao.save(employee);
        empService.update(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id) {
        //employeeDao.delete(id);
        empService.delete(id);
        return "redirect:/emps";
    }

    @GetMapping("/empByName")
    public String getByName(@RequestParam(value = "name") String name, Model model) {
        List<Employee> employees = empService.searchByName(name);
        if (employees.size() > 0) {
            List<EmpDto> empDtos = getEmpDtos(employees);
            model.addAttribute("emps", empDtos);
        }else {
            Department department = departmentService.selectByDepartmentId(name);
            System.out.println(department.toString());
            if(department != null) {
                List<Employee> list = empService.searchByBmid(department.getId());
                List<EmpDto> empDtos = getEmpDtos(list);
                model.addAttribute("emps",empDtos);
            }
        }

        return "emp/search";
    }

    private List<EmpDto> getEmpDtos(List<Employee> employees) {
        List<Integer> departmentIds = employees.stream().map(Employee::getDepartmentId).collect(Collectors.toList());
        departmentIds.forEach(e -> System.out.println(e));
        List<Department> departments = departmentService.selectByDepartmentIds(departmentIds);
        System.out.println("department size : " + departments.size());
        List<EmpDto> empDtos = new ArrayList<>();
        employees.forEach(e -> {
            EmpDto empDto = new EmpDto();
            BeanUtils.copyProperties(e, empDto);
            for (Department department : departments) {
                if (e.getDepartmentId() == department.getId())
                    empDto.setDepartmentName(department.getDepartmentName());
            }
            System.out.println(empDto.getDepartmentName());
            empDtos.add(empDto);
        });
        return empDtos;
    }
    @GetMapping("/checkEmail")
    @ResponseBody
    public Map<String,Boolean> checkEmial(@RequestParam("email") String email){
        Map<String,Boolean> map = new HashMap();
        Boolean aBoolean = empService.checkEmail(email);
        map.put("isHead",aBoolean);
        return map;
    }


}
