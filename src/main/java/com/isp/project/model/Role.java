package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Role")
public class Role {

@Id
@Column(name = "RoleID")
@GeneratedValue(strategy = GenerationType.AUTO)
private int roleID;

@Column(name = "RoleName")
private String roleName;

public int getRoleID() {
    return roleID;
}

public void setRoleID(int roleID) {
    this.roleID = roleID;
}

public String getRoleName() {
    return roleName;
}

public void setRoleName(String roleName) {
    this.roleName = roleName;
}





}
