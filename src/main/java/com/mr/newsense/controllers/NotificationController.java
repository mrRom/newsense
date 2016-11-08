package com.mr.newsense.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.Article;
import com.mr.newsense.models.User;

@RestController
public class NotificationController {
    //TODO
    //Has to do some tests
    private static final Logger log = LoggerFactory
	    .getLogger(NewsController.class);
    @Autowired
    ArticleDao articleDao;
    @Autowired
    UserDao userDao;

    @RequestMapping("/notify")
    public ResponseEntity<String> sendMessage(HttpServletRequest request) {
	final String username = SecurityContextHolder.getContext()
		.getAuthentication().getName();
	User user = userDao.getUserByName(username);
	Article article = (Article) request.getSession().getAttribute(
		"lastShowedArticle");
	try {
	    Thread.sleep(20000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	if (article != null){
        	long numberOfArticlesAfterDate = articleDao.getNumberOfArticlesAfterDate(article
        		.getPublishDate(), user);
        	log.debug(article.toString());
        	log.debug("" + numberOfArticlesAfterDate);
        	if (numberOfArticlesAfterDate > 0) {
        	    return new ResponseEntity<String>("data:" + numberOfArticlesAfterDate
        		    + "\n\n", HttpStatus.OK);
        	} else {
        	    return new ResponseEntity<String>("" + numberOfArticlesAfterDate,
        		    HttpStatus.OK);
        	}
	} else {
	    return new ResponseEntity<String>("",
		    HttpStatus.OK);
	}
    }
}
