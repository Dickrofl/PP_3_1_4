package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    WebSecurityConfig webSecurityConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveUser(User user) {
        String hashedPassword = webSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        String hashedPassword = webSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }
}


