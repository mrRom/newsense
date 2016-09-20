package com.mr.newsense.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.User;

@RestController
public class RegistrationController {
    
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    
    @Autowired
    UserDao userDao; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @RequestMapping(value = "/register", method = RequestMethod.POST) 
    public ResponseEntity<String> register(@RequestBody User user) {
	user.setEnabled(true);
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	userDao.createUser(user);
	log.info("New user was registered:" + user);
	return new ResponseEntity<String>(HttpStatus.OK); 
    }
}
