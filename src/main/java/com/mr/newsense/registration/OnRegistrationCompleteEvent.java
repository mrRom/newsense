package com.mr.newsense.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.mr.newsense.models.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = -341016644560132061L;
    
    private final String appUrl;
    private final Locale locale;
    private final User user;
 
    public OnRegistrationCompleteEvent(
      User user, Locale locale, String appUrl) {
        super(user);
         
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
}
