package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getByIdRole(Long id) {
        return roleDao.getByIdRole(id);
    }

    @Override
    public List<Role> getListRoles() {
        return roleDao.getListRoles();
    }

    @Override
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    public List<Role> getListByName(List<String> name) {
        return roleDao.getListByName(name);
    }

    @Override
    public boolean add(Role user) {
        return roleDao.add(user);
    }
}
