package com.dh.webservice.service;

import com.dh.webservice.domain.User;

public interface UserService {
    public User findUserByUserEmail(String email);
    public void saveUser(User user);
}
