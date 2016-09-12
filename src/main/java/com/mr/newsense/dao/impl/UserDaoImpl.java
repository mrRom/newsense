package com.mr.newsense.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.User;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    public UserDaoImpl() {

    }
    
    public UserDaoImpl(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }
    
    public void createUser(User user){
	sessionFactory.getCurrentSession().save(user);
	//TODO
	sessionFactory.getCurrentSession().createSQLQuery(
		"insert into user_roles (username, role) values (\'" + user.getUsername() + "\', \'ROLE_USER\')")
		.executeUpdate();
    }
}
