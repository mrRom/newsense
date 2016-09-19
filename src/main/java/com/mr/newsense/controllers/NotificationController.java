package com.mr.newsense.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.models.Article;

@RestController
public class NotificationController {
    private static final Logger log = LoggerFactory
	    .getLogger(NewsController.class);
    @Autowired
    ArticleDao articleDao;

    @RequestMapping("/notify")
    public ResponseEntity<String> sendMessage(HttpServletRequest request) {
	Article article = (Article) request.getSession().getAttribute(
		"lastShowedArticle");
	try {
	    Thread.sleep(20000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	if (article != null){
        	long numberOfArticlesAfterDate = articleDao.getNumberOfArticlesAfterDate(article
        		.getPublishDate());
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
