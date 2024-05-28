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
private int RoleID;

@Column(name = "RoleName")
private String RoleName;



public Role(int roleID, String roleName) {
    RoleID = roleID;
    RoleName = roleName;
}

public Role() {
}

public int getRoleID() {
    return RoleID;
}

public void setRoleID(int roleID) {
    RoleID = roleID;
}

public String getRoleName() {
    return RoleName;
}

public void setRoleName(String roleName) {
    RoleName = roleName;
}


}
