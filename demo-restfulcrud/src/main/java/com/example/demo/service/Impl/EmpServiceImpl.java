package com.example.demo.service.Impl;

import com.example.demo.Mapper.EmpMapper;
import com.example.demo.entities.Employee;
import com.example.demo.service.EmpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 17:08$  2019-1-12$
 * @return :
 */

@Service
public class EmpServiceImpl implements EmpService {
    @Resource
    private EmpMapper empMapper;
    @Override
    public void add(Employee employee) {
        empMapper.add(employee);
    }

    @Override
    public void delete(Integer id) {
        empMapper.delete(id);
    }

    @Override
    public List<Employee> selectAll() {
        return empMapper.selectAll();
    }

    @Override
    public Employee selectById(Integer id) {
        return empMapper.selectById(id);
    }

    @Override
    public int update(Employee employee) {
        return empMapper.update(employee);
    }

    @Override
    public List<Employee> searchByName(String name) {
        name = "%" + name + "%";
        return empMapper.searchByName(name);
    }

    @Override
    public void deleteByBmId(Integer id) {
        empMapper.deleteByBmId(id);
    }

    @Override
    public List<Employee> searchByBmid(Integer departmentId) {
        return empMapper.searchByBmid(departmentId);
    }

    @Override
    public Integer addAll(List<Employee> employees) {
        return empMapper.addAll(employees);
    }

    @Override
    public Boolean checkEmail(String email) {
        List<Employee> employees = empMapper.checkEmail(email);
        if(employees.size() > 0){
            return true;
        }
        return false;
    }


}
