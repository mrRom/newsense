package com.mr.newsense.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mr.newsense.dao.TokenDao;
import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.User;
import com.mr.newsense.models.VerificationToken;
import com.mr.newsense.validation.EmailExistsException;
import com.mr.newsense.validation.UserNameExistsException;

@Service("userService")
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private TokenDao tokenDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByVerificationToken(String verificationToken) {
	final VerificationToken token = tokenDao.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
	return tokenDao.findByToken(verificationToken);
    }
    
    public User registerNewUserAccount(User user) 
	      throws EmailExistsException, UserNameExistsException  { 
	        if (emailExist(user.getEmail())) {
	            throw new EmailExistsException(
	              "Account with this email adress already exists: "
	              + user.getEmail());
	        }
	        
	        if (userNameExist(user.getUsername())) {
	            throw new UserNameExistsException(
	              "Account with this username already exists: "
	              + user.getUsername());
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setEnabled(false);
	        userDao.createUser(user);
	        return userDao.getUserByName(user.getUsername());
	    }
    
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenDao.findByToken(token);
        if (verificationToken == null) {
            return "invalidToken";
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenDao.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        tokenDao.delete(verificationToken);
        userDao.updateUser(user);
        return "valid";
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenDao.save(myToken);
    }
    
    private boolean emailExist(String email) {
        User user = userDao.getUserByEmail(email);
        return user != null ? true: false;
    }
    
    private boolean userNameExist(String userName) {
        User user = userDao.getUserByName(userName);
        return user != null ? true: false;
    }  
}
