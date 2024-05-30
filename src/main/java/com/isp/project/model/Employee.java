package com.isp.project.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String idenId;
    
    

    public Employee(int id, String fullName, String email, String phone, String address, String idenId, Date dob,
            String userName, String password, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.idenId = idenId;
        this.dob = dob;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    @DateTimeFormat(pattern ="dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dob;

    public Employee() {
    }
   
    public Date getDob() {
        return dob;
    }

    private String userName;
    private String password;

    public String getIdenId() {
        return idenId;
    }

    public void setIdenId(String idenId) {
        this.idenId = idenId;
    }

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="RoleId")
    private Role role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
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
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
