package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @Column(name = "EmployeeID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int EmployeeID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Phone")
    private String Phone;

    @Column(name = "Address")
    private String Address;

    @Column(name = "IdentificationID")
    private String IdentificationID;

    @Column(name = "Dob")
    private Date Dob;

    @Column(name = "Username")
    private String Username;

    @Column(name = "Password")
    private String Password;

    @Column(name = "RoleID")
    private int RoleID;

    

    public Employee(int employeeID, String name, String email, String phone, String address, String identificationID,
            Date dob, String username, String password, int roleID) {
        EmployeeID = employeeID;
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        IdentificationID = identificationID;
        Dob = dob;
        Username = username;
        Password = password;
        RoleID = roleID;
    }

    public Employee() {
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIdentificationID() {
        return IdentificationID;
    }

    public void setIdentificationID(String identificationID) {
        IdentificationID = identificationID;
    }

    public Date getDob() {
        return Dob;
    }

    public void setDob(Date dob) {
        Dob = dob;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    

}
