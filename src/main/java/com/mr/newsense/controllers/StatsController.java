package com.mr.newsense.controllers;

import info.debatty.java.stringsimilarity.Jaccard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.ArticleDao;
import com.mr.newsense.models.Article;

@RestController
@RequestMapping(value = "/stats")
public class StatsController {
    
    @Autowired
    ArticleDao articleDao;
    
    @RequestMapping(value = "/similar/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getAllStats(@PathVariable long id) {
	List<Article> articles = articleDao.getArticles(2);
	Article art = articleDao.getArticleById(id);
	List<Article> result = new ArrayList<Article>();
	//TODO
	//Have to find a way to verify if news are similar
	Jaccard jaccard = new Jaccard();
	for (Article article : articles) {
	    Double similarity = jaccard.similarity(art.getTitle(), article.getTitle());
	    if (similarity > 0.2d){
		result.add(article);
	    }
	    similarity = jaccard.similarity(art.getDescription(), article.getDescription());
	    if (similarity > 0.2d){
		if (!result.contains(article)){
		    result.add(article);
		}
	    }
	}
	return new ResponseEntity<List<Article>>(result, HttpStatus.OK);
    }
}
