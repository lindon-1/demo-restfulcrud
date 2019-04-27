package com.example.demo.service;

import com.example.demo.entities.Employee;

import java.util.List;

public interface EmpService {
    public void add(Employee employee);

    public void delete(Integer id);

    public List<Employee> selectAll();

    public Employee selectById(Integer id);

    public int update(Employee employee);

    public List<Employee> searchByName(String name);

    public void deleteByBmId(Integer id);

    public List<Employee> searchByBmid(Integer departmentId);

    public Integer addAll(List<Employee> employees);

    public Boolean checkEmail(String email);

}
