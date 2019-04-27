package com.example.demo.service;

import com.example.demo.entities.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    public int add(Department department);

    public void delete(String departmentId);

    public List<Department> selectAll();

    public int update(Department department);

    public Department selectByDepartmentId(String departmentId);

    public List<Department> selectByDepartmentIds(List<Integer> departmentIds);

    public Map<String,List> getCount();

    public Boolean checkDepart(String departmentId,String departmentName);

    public Department searchById(Integer id);

    public Integer searchIdByName(String departmentName);
}
