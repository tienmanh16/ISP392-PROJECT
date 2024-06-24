package com.isp.project.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Employee")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

@NoArgsConstructor
public class Employee {
    @Id
    @Column(name = "EmployeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name="Name")
    private String fullName;
    private int gender;
    private String email;
    private String phone;
    private String address;
    @Column(name="IdentificationID")
    private String idenId;
    private int salary;
    @Column(name = "Role")
    private String role;
    @Column(name = "IsActive")
    private Boolean isActive;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date dob;
    private String username;
    private String password;


    
    public Employee(String address, Date dob, String email, String fullName, int gender, int id, String idenId, Boolean isActive, String password, String phone, List<Register> register, String role, int salary, String username) {
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.id = id;
        this.idenId = idenId;
        this.isActive = isActive;
        this.password = password;
        this.phone = phone;
        this.register = register;
        this.role = role;
        this.salary = salary;
        this.username = username;
    }

    public String getIdenId() {
        return idenId;
    }

    public void setIdenId(String idenId) {
        this.idenId = idenId;
    }
   
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return phone;
    }

    public void setTelephone(String telephone) {
        this.phone = telephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToMany(mappedBy = "employeeID")
     @JsonManagedReference
    private List<Register> register;



    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getDob() {
        return dob;
    }

    public List<Register> getRegister() {
        return register;
    }

    public void setRegister(List<Register> register) {
        this.register = register;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
