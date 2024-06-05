package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Role")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Role {
    @Id
    @Column(name="RoleID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="RoleName")
    private String name;

    @OneToMany(fetch=FetchType.LAZY ,mappedBy = "role")
    @JsonManagedReference
	private List<Employee> employee;

}