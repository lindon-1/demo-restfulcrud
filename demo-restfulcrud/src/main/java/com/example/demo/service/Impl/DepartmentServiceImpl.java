package com.example.demo.service.Impl;

import com.example.demo.Mapper.DepartmentMapper;
import com.example.demo.Mapper.EmpMapper;
import com.example.demo.entities.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmpMapper empMapper;

    @Override
    public int add(Department department) {
        return departmentMapper.add(department);
    }

    @Override
    public void delete(String departmentId) {
        departmentMapper.delete(departmentId);
    }

    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public int update(Department department) {
        return departmentMapper.update(department);
    }

    @Override
    public Department selectByDepartmentId(String departmentId) {
        return departmentMapper.selectByDepartmentId(departmentId);
    }

    @Override
    public List<Department> selectByDepartmentIds(List<Integer> departmentIds) {
        return departmentMapper.selectByDepartmentIds(departmentIds);
    }

    /**
     * @return
     * @Author lindonglin
     * @Description 获取各部门名称和人数
     * @Param
     * @Date 10:43 2019/1/13
     */

    @Override
    public Map<String, List> getCount() {
        List<Integer> count = empMapper.getCount();
        //各个部门职工人数
        List<Integer> bmidPerCounts = new ArrayList<>();
        //职工所在部门id
        List<Integer> bmid = empMapper.getBmid();
        List<String> bmmc = departmentMapper.getBmmc();
        List<Department> departments = departmentMapper.selectAll();
        for(Department d : departments){
            boolean flag = false;
            for(Integer id : bmid){
                if(d.getId() == id)
                    flag = true;
            }
            if(flag)
                bmidPerCounts.add(count.get(bmid.indexOf(d.getId())));
            else
                bmidPerCounts.add(0);
        }

        Map<String, List> map = new HashMap<>();
        bmidPerCounts.forEach(e -> System.out.println(e));
        bmmc.forEach(e -> System.out.println(e));
        map.put("bmmc", bmmc);
        map.put("count", bmidPerCounts);
        return map;
    }

    @Override
    public Boolean checkDepart(String departmentId, String departmentName) {
        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(departmentName);
        List<Department> departments = departmentMapper.checkDepart(department);
        if(departments.size() > 0){
            return  false;
        }
        return true;
    }

    @Override
    public Department searchById(Integer id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public Integer searchIdByName(String departmentName) {
        return departmentMapper.searchIdByName(departmentName);
    }
}
