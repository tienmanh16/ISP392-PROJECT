package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.Role;
import com.isp.project.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void delete(Role entity) {
        roleRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<Role> entities) {
        roleRepository.deleteAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public List<Role> findAllById(List<Integer> ids) {
        return (List<Role>)roleRepository.findAllById(ids);
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public List<Role> saveAll(List<Role> entities) {
        return (List<Role>) roleRepository.saveAll(entities);
    }

    
}
