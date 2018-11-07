package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@Override
    public UserRoleModel findByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public void updatePassword(String password, String username) {
        UserRoleModel user = findByUsername(username);
        String hashedPassword = encrypt(password);
        user.setPassword(hashedPassword);
        userDb.save(user);
    }
}
