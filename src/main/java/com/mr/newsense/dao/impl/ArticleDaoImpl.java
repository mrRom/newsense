package com.mr.newsense.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
	Query query;
	if (sources.size()>0){
	    String condition = " where";
	    for (String source: sources) {
		condition = condition + " url like \'%" + source + "%\' or"; 
	    }
	    condition = condition.substring(0, condition.length() - 2);
	    query = sessionFactory.getCurrentSession().createQuery(
			"from Article" + condition + "order by publishDate desc");
	} else {
	    query = sessionFactory.getCurrentSession().createQuery(
			"from Article order by publishDate desc");
	}
	query.setFirstResult(quantity * step);
	query.setMaxResults(quantity);
	List<Article> result = query.list();
	System.out.println(result.size());
	return result;
    }

    @SuppressWarnings("unchecked")
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
    public long getNumberOfArticlesAfterDate(Date date, User user) {
	Set<Source> sources = user.getSources();
	String sql = "select count(*) FROM Article AS A WHERE A.publishDate > :endDate";
	if (user!=null){
	    sql = sql + " AND ( ";
	    StringBuffer sb = new StringBuffer(sql);
		for (Source s: sources){
		    sb.append("A.url LIKE \'%"+s.getHost()+"%\' OR ");
		}
	    sb.delete(sb.length() - 3, sb.length());
	    sb.append(")");
	    sql = sb.toString();
	}
	long result = (long) sessionFactory.getCurrentSession()
	.createQuery(sql)
	.setParameter("endDate", date)
	.uniqueResult();
	return result;
    }
}
