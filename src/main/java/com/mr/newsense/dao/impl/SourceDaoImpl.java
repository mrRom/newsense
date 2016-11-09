package com.mr.newsense.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.SourceDao;
import com.mr.newsense.models.Source;

@Repository("sourceDao")
@Transactional
public class SourceDaoImpl implements SourceDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    public SourceDaoImpl() {

    }
    
    public SourceDaoImpl(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Set<Source> selectSourcesByHostNames(List<String> hosts) {
	Set<Source> sources = new HashSet<Source>();
	if (hosts.size() == 0){
	    return sources;
	}
	Disjunction disjunction = Restrictions.disjunction();
	for (String host: hosts){
	    disjunction.add(Restrictions.eq("host", host));
	}
	sources = new HashSet<Source>(sessionFactory.getCurrentSession().createCriteria(Source.class)
		.add(disjunction)
		.list());
	return sources;
    }
}
