package com.mr.newsense.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    
    @Override
    public void createUser(User user){
	sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUserByName(String name) {
	return (User)sessionFactory.getCurrentSession().createCriteria(User.class)
		.add(Restrictions.eq("username", name)).uniqueResult();
    }

    @Override
    public User getUserById(Long id) {
	return (User)sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void updateUser(User user) {
	sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public void deleteUser(User user) {
	sessionFactory.getCurrentSession().delete(
		sessionFactory.getCurrentSession()
		.contains(user) ? user : sessionFactory.getCurrentSession().merge(user));
    }

    @Override
    public User getUserByEmail(String email) {
	return (User)sessionFactory.getCurrentSession().createCriteria(User.class)
		.add(Restrictions.eq("email", email)).uniqueResult();
    }
}
