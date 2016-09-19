package com.mr.newsense.dao;

import com.mr.newsense.models.User;

public interface UserDao {
    void createUser(User user);
    User getUserByName(String name);
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUser(User user);
}