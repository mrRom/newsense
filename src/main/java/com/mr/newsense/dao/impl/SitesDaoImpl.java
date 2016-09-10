package com.mr.newsense.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.SitesDao;

@Repository("sitesDao")
@Transactional
public class SitesDaoImpl implements SitesDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SitesDaoImpl() {

    }

    public SitesDaoImpl(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getAllSelectedByUserSites(String userName) {
	//TODO
	//get rid of native sql
	return sessionFactory.getCurrentSession().createSQLQuery(
		"select site from selected_sites where user_name = :userName")
		.setParameter("userName", userName).list();
    }

    @Override
    public void createUpdateSelectedUserSites(String userName, List<String> sites) {
	//TODO
	//get rid of native sql
	deleteUserSites(userName);
	System.out.println(sites);
	String s = ""; 
	for (String site: sites) {
	    s = s + "(\"" + userName + "\",\"" + site + "\"),";
	}
	s = s.substring(0, s.length()-1) + ";";
	System.out.println(s);
	    sessionFactory.getCurrentSession().createSQLQuery(
			"insert into selected_sites (user_name, site) values" + s).executeUpdate();
    }

    @Override
    public void deleteUserSites(String userName) {
	//TODO
	//get rid of native sql
	sessionFactory.getCurrentSession().createSQLQuery(
		"delete from selected_sites where user_name=:userName")
		.setParameter("userName", userName).executeUpdate();
    }
}
