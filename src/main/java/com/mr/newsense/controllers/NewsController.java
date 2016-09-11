package com.mr.newsense.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.dao.SitesDao;
import com.mr.newsense.models.Article;

@RestController
public class NewsController {
    private static final Logger log = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    ArticleDao articleDao;
    @Autowired
    SitesDao sitesDao;
    
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getAllArticles() {
	final String username = SecurityContextHolder.getContext()
		.getAuthentication().getName();
	//TODO
	List<String> sources = sitesDao.getAllSelectedByUserSites(username);
	List<Article> articles = articleDao.getAllArticles(sources);
        if(articles.isEmpty()){
            return new ResponseEntity<List<Article>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/news/more/{step}", method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getMoreArticles(@PathVariable int step, HttpServletRequest request) {
	final String username = SecurityContextHolder.getContext()
		.getAuthentication().getName();
	log.info("Username:" + username);
	int quantity = 20;
	//TODO
	List<String> sources = sitesDao.getAllSelectedByUserSites(username);
	log.info("Sources:" + sources);
	List<Article> articles = articleDao.getMoreArticles(quantity, step, sources);
	if (step == 0){
	    request.getSession().setAttribute("lastShowedArticle", articles.get(0));
	    log.info(request.getSession().getAttribute("lastShowedArticle").toString());
	}
        if(articles.isEmpty()){
            return new ResponseEntity<List<Article>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }
}
