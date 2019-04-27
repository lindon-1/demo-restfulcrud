package com.example.demo.Dto;

import java.util.Date;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 17:57  2019/1/12
 * @return :
 */
public class EmpDto {

    private Integer id;
    private String name;

    private String email;
    //1 male, 0 female
    private Integer gender;
    private String departmentId;
    private String departmentName;
    private Date birth;

    public EmpDto() {
    }

    public EmpDto(Integer id, String name, String email, Integer gender, String departmentId, String departmentName, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.birth = birth;
    }

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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "EmpDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", birth=" + birth +
                '}';
    }
}
