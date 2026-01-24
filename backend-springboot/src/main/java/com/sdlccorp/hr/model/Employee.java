package com.sdlccorp.hr.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("employees")
public class Employee {
    @Id
    private String id;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String department;

    private double salary;

    public Employee() {}

    public Employee(String fullName, String email, String department, double salary) {
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public void setId(String id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
}
