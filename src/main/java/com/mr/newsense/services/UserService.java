package com.mr.newsense.services;

import com.mr.newsense.models.User;
import com.mr.newsense.models.VerificationToken;
import com.mr.newsense.validation.EmailExistsException;
import com.mr.newsense.validation.UserNameExistsException;

public interface UserService {
    User getUserByVerificationToken(String verificationToken);
    VerificationToken getVerificationToken(String verificationToken);
    void createVerificationToken(User user, String token);
    User registerNewUserAccount(User user) throws EmailExistsException, UserNameExistsException;
    String validateVerificationToken(String token);
}
