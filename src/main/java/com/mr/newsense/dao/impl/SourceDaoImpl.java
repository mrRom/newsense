package com.mr.newsense.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
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
    
    @Override
    public Set<Source> selectSourcesByHostNames(List<String> hosts) {
	//TODO
	//Have to make it without loop (as one select statement)
	Set<Source> sources = new HashSet<Source>();
	for (String host: hosts){
	    sources.add((Source)sessionFactory.getCurrentSession().createCriteria(Source.class)
			.add(Restrictions.eq("host", host))
			.uniqueResult());
	}
	return sources;
    }
}
