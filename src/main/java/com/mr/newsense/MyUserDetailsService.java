package com.mr.newsense;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.User;
import com.mr.newsense.models.UserRole;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserDao userDao;
    
    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username)
	    throws UsernameNotFoundException {
	    User user = userDao.getUserByName(username);
	    List<GrantedAuthority> authorities =
                              buildUserAuthority(user.getUserRole());

	    return buildUserForAuthentication(user, authorities);
    }
    
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(
	    User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
			user.getPassword(),user.isEnabled(), true, true, true, authorities);
    }
    
    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
	for (UserRole userRole : userRoles) {
		setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
	}

	List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
	return result;
    }
}
