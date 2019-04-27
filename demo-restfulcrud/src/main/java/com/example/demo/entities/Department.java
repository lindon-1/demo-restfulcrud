package com.example.demo.entities;

public class Department {

	private Integer id;
	private String departmentId;
	private String departmentName;

	public Department() {
	}
	
	public Department(int i, String string) {
		this.id = i;
		this.departmentName = string;
	}

	public Department(Integer id, String departmentId, String departmentName) {
		this.id = id;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", departmentId='" + departmentId + '\'' +
				", departmentName='" + departmentName + '\'' +
				'}';
	}
}
