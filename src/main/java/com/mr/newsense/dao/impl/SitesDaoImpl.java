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
	return (List<String>) sessionFactory.getCurrentSession().createSQLQuery(
		"select * from selected_sites s where s.user_name = :userName")
		.setParameter("userName", userName);
    }

    @Override
    public void createUpdateSelectedUserSites(String userName, List<String> sites) {
	List<String> curentlySelectedSites = getAllSelectedByUserSites(userName);
	for (String site: sites) {
	    if (!curentlySelectedSites.contains(site)){
		sessionFactory.getCurrentSession().createSQLQuery(
			"insert into selected_sites (user_name, site) values (:userName, :site)")
			.setParameter("userName", userName)
			.setParameter("site", site);
	    }
	}
    }
}
