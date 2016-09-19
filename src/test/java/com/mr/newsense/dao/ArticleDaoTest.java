package com.mr.newsense.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mr.newsense.UiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class ArticleDaoTest {

    @Autowired
    ArticleDao articleDao;
    
    @Test
    public void getMoreArticlesTest(){
	/*List<String> list = new ArrayList<>();
	list.add("test");
	System.out.println(articleDao.getMoreArticles(20, 0, list));*/
    }
}
