package com.example.demo.Mapper;

import com.example.demo.entities.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    public int add(Department department);

    public void delete(@Param("bmbh") String departmentId);

    public List<Department> selectAll();

    public int update(Department department);


    /**
    * @Author lindonglin
    * @Description //TODO 根据部门编号获取部门
    * @Param
    * @Date 8:51 2019/3/9
    * @return
    */

    public Department selectByDepartmentId(@Param("bmbh") String departmentId);


    /**
    * @Author lindonglin
    * @Description //TODO 根据id集合获取部门
    * @Param
    * @Date 8:51 2019/3/9
    * @return
    */

    public List<Department> selectByDepartmentIds(@Param("departmentIds") List<Integer> departmentIds);


    /**
    * @Author lindonglin
    * @Description 查询各部门的人数
    * @Param
    * @Date 10:36 2019/1/13
    * @return
    */

    public List<String> getBmmc();


    /**
    * @Author lindonglin
    * @Description //TODO 校验该部门是否存在
    * @Param
    * @Date 11:24 2019/2/12
    * @return
    */
    public List<Department> checkDepart(Department department);


    /**
    * @Author lindonglin
    * @Description //TODO 根据部门id查department
    * @Param
    * @Date 20:42 2019/2/12
    * @return
    */
    public Department selectById(Integer id);


    /**
    * @Author lindonglin
    * @Description //TODO 根据部门名称查找id
    * @Param
    * @Date 22:42 2019/2/12
    * @return
    */
    public Integer searchIdByName(String departmentName);



}
