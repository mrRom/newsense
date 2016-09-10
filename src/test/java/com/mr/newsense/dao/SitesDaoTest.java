package com.mr.newsense.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mr.newsense.UiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@WebAppConfiguration
public class SitesDaoTest {    
    
    @Autowired
    SitesDao sitesDao;
    /*
    @Test
    public void getAllSelectedByUserSitesTest(){
	sitesDao.getAllSelectedByUserSites("user");
    }
    
    @Test
    public void deleteUserSitesTest(){
	sitesDao.deleteUserSites("user");
    }
    
    @Test
    public void createUpdateSelectedUserSitesTest(){
	List<String> list = new ArrayList<String>();
	list.add("test1");
	list.add("test2");
	sitesDao.createUpdateSelectedUserSites("user", list);;
    }
    */
}
