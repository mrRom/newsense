package com.mr.newsense.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.models.Article;
import com.mr.newsense.models.Source;
import com.mr.newsense.models.User;

@Repository("articleDao")
@Transactional
public class ArticleDaoImpl implements ArticleDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    public ArticleDaoImpl() {

    }

    public ArticleDaoImpl(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Article> getAllArticles(List<String> sources) {
	@SuppressWarnings("unchecked")
	List<Article> list = (List<Article>) sessionFactory.getCurrentSession()
		.createCriteria(Article.class).list();
	return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getMoreArticles(int quantity, int step, List<String> sources) {
	Disjunction disjunction = Restrictions.disjunction();
	if (sources.size()>0){
    	    for (String source: sources) {
    		disjunction.add(Restrictions.like("url", source, MatchMode.ANYWHERE));
    	    }
	}
	List<Article> result = sessionFactory.getCurrentSession()
		.createCriteria(Article.class)
		.add(disjunction)
		.addOrder(Order.desc("publishDate"))
		.setFirstResult(quantity * step)
		.setMaxResults(quantity)
		.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticles(int period) {
	//period - negative number. Days from current date.
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_YEAR, period);
	Date fromDate = c.getTime();
	Date toDate = new Date();
	List<Article> list = sessionFactory.getCurrentSession()
		.createCriteria(Article.class)
		.add(Restrictions.ge("publishDate", fromDate))
		.add(Restrictions.lt("publishDate", toDate))
		.list();
	return list;
    }

    @Override
    public Article getArticleById(long id) {
	return (Article)sessionFactory.getCurrentSession().get(Article.class, id);
    }
    
    @Override
    public long getNumberOfArticlesAfterDate(Date date, User user) {
	Disjunction disjunction = Restrictions.disjunction();
	if (user!=null){
	    Set<Source>sources = user.getSources();
	    for (Source source: sources){
		disjunction.add(Restrictions.like("url", source.getHost(), MatchMode.ANYWHERE));
	    }
	}
	long result = (long) sessionFactory.getCurrentSession()
		.createCriteria(Article.class)
		.add(Restrictions.ge("publishDate", date))
		.add(disjunction)
		.setProjection(Projections.rowCount())
		.uniqueResult();
	return result;
    }
}
