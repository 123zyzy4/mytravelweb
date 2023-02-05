package com.zy.travel.dao;


import com.zy.travel.domain.User;


public interface IUserDao {

    User findByUsername(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
