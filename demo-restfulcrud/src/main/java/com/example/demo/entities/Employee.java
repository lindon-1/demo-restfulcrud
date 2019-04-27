package com.example.demo.entities;

import com.example.demo.entities.Department;

import java.util.Date;

public class Employee {

	private Integer id;
    private String name;

    private String email;
    //1 male, 0 female
    private Integer gender;
    //存放部门id
    private Integer departmentId;
    private Date birth;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Employee() {
    }


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Employee(Integer id, String name, String email, Integer gender, Integer departmentId, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", departmentId='" + departmentId + '\'' +
                ", birth=" + birth +
                '}';
    }
}
