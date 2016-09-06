package com.mr.newsense.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.models.Article;

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
    public List<Article> getAllArticles() {
	@SuppressWarnings("unchecked")
	List<Article> list = (List<Article>) sessionFactory.getCurrentSession()
		.createCriteria(Article.class).list();
	return list;
    }

    @Override
    public List<Article> getMoreArticles(int quantity, int step) {
	Query query = sessionFactory.getCurrentSession().createQuery(
		"from Article order by publishDate desc");
	query.setFirstResult(quantity * step);
	query.setMaxResults(quantity);
	List<Article> result = query.list();
	System.out.println(result.size());
	return result;
    }

    @Override
    public List<Article> getArticles(int period) {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_YEAR, -2);
	Date fromDate = c.getTime();
	Date toDate = new Date();
	List<Article> list = sessionFactory
		.getCurrentSession()
		.createQuery(
			"from Article where publishDate BETWEEN :fromDate AND :toDate")
		.setParameter("fromDate", fromDate)
		.setParameter("toDate", toDate).list();
	return list;
    }

    @Override
    public Article getArticleById(long id) {
	return (Article)sessionFactory.getCurrentSession().get(Article.class, id);
    }
    
    @Override
    public long getNumberOfArticlesAfterDate(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String endDate = format.format(date);
	long result = (long) sessionFactory.getCurrentSession()
	.createQuery("select count(*) FROM Article AS c WHERE c.publishDate > :endDate")
	.setParameter("endDate", date)
	.uniqueResult();
	return result;
    }
}
