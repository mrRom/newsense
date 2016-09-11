package com.mr.newsense.dao;

import java.util.Date;
import java.util.List;

import com.mr.newsense.models.Article;

public interface ArticleDao {
    List<Article> getAllArticles(List<String> sources);
    List<Article> getMoreArticles(int quantity, int step, List<String> sources);
    List<Article> getArticles(int period);
    Article getArticleById(long id);
    long getNumberOfArticlesAfterDate(Date date);
}
