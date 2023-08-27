package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    Role getByIdRole(Long id);

    List<Role> getListRoles();

    Role getByName(String name);

    List<Role> getListByName(List<String> name);

    boolean add(Role user);
}
