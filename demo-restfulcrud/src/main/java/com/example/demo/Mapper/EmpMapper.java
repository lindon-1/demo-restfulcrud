package com.example.demo.Mapper;

import com.example.demo.entities.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 17:12  2019/1/12
 * @return :
 */
@Mapper
public interface EmpMapper {

    public void add(Employee employee);

    public void delete(Integer id);

    public List<Employee> selectAll();

    public Employee selectById(Integer id);

    public int update(Employee employee);


    /**
    * @Author lindonglin
    * @Description 获取职工所在部门的人数
    * @Param
    * @Date 14:03 2019/1/13
    * @return
    */
    public List<Integer> getCount();

    public List<Integer> getBmid();

    
    /**
    * @Author lindonglin
    * @Description //TODO 模糊查询
    * @Param 
    * @Date 18:46 2019/2/11
    * @return 
    */
    
    public List<Employee> searchByName(String name);


    /**
    * @Author lindonglin
    * @Description //TODO 根据部门id删除员工
    * @Param
    * @Date 18:48 2019/2/11
    * @return
    */
    public int deleteByBmId(Integer bmid);


    /**
    * @Author lindonglin
    * @Description //TODO 根据部门id查出员工
    * @Param
    * @Date 20:02 2019/2/12
    * @return
    */
    public List<Employee> searchByBmid(Integer departmentId);


    /**
    * @Author lindonglin
    * @Description //TODO 批量插入员工
    * @Param
    * @Date 22:57 2019/2/12
    * @return
    */
    public Integer addAll(List<Employee> employees);


    /**
    * @Author lindonglin
    * @Description //TODO 查询Email是否存在
    * @Param
    * @Date 19:05 2019/2/21
    * @return
    */
    public List<Employee> checkEmail(String email);


}
