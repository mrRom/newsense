package com.mr.newsense.controllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.models.User;
import com.mr.newsense.registration.OnRegistrationCompleteEvent;
import com.mr.newsense.services.UserService;
import com.mr.newsense.validation.EmailExistsException;
import com.mr.newsense.validation.UserNameExistsException;

@RestController
public class RegistrationController {
    
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @RequestMapping(value = "/register", method = RequestMethod.POST) 
    public ResponseEntity<String> register(@RequestBody User user, final HttpServletRequest request) {
	try {
	    User registeredUser = userService.registerNewUserAccount(user);
	    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), getAppUrl(request)));
	} catch (EmailExistsException | UserNameExistsException e) {
	    log.info(e.getMessage());
	    return new ResponseEntity<String>(HttpStatus.OK);
	}
	log.info("New user was registered:" + user);
	return new ResponseEntity<String>(HttpStatus.OK); 
    }
    
    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            return new ResponseEntity<String>("Email was confirmed!", HttpStatus.OK);
        } else if (result.equals("expired")){
            return new ResponseEntity<String>("Link is expired!", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<String>("Link is not valid!", HttpStatus.BAD_REQUEST);
        }
        
    }
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
