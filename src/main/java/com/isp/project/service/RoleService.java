package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.model.Role;

@Service
public interface RoleService {

    Role findByName(String name);

    void delete(Role entity);

    void deleteAll();

    void deleteAll(List<Role> entities);

    void deleteById(Integer id);

    List<Role> findAll();

    List<Role> findAllById(List<Integer> ids);

    Role save(Role entity);

    List<Role> saveAll(List<Role> entities);

}
