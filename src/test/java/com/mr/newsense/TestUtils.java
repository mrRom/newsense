package com.mr.newsense;

import java.util.HashSet;
import java.util.Set;

import com.mr.newsense.models.Source;
import com.mr.newsense.models.User;
import com.mr.newsense.models.UserRole;

public class TestUtils {
    public User createBasicTestUser(){
	User user = new User();
	Set<UserRole> roles = new HashSet<>();
	UserRole role = new UserRole(user, "ROLE_USER");
	user.setUsername("test");
	user.setPassword("test");
	user.setEmail("test@test.com");
	roles.add(role);
	user.setUserRole(roles);
	user.setEnabled(true);
	return user;
    }
    
    public Source createTestSource(String host, String url){
	Source source = new Source();
	source.setHost(host);
	source.setUrl(url);
	return source;
    }
}
