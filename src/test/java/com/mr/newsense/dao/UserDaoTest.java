package com.mr.newsense.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mr.newsense.UiApplication;
import com.mr.newsense.models.Source;
import com.mr.newsense.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    User user = new User();
    Source source = new Source();
    
    @Before
    public void createUserTest(){
	user.setUsername("test");
	user.setPassword("test");
	user.setEnabled(1);
	source = new Source();
	source.setHost("test1");
	source.setUrl("test1/test1");
	Set<Source> sources = new HashSet<>();
	sources.add(source);
	user.setSources(sources);
	userDao.createUser(user);
    }
    
    @Test
    public void getUserByNameTest(){
	User myuser = userDao.getUserByName("test");
	assertEquals(myuser.getUsername(), "test");
	assertTrue(myuser.getSources().contains(source));
    }

    @Test
    public void updateUserTest(){
	Source anotherSource = new Source();
	anotherSource.setHost("test2");
	anotherSource.setUrl("test2/test2");
	user.getSources().add(anotherSource);
	userDao.updateUser(user);
	User myuser = userDao.getUserByName("test");
	assertTrue(myuser.getSources().contains(anotherSource));
    }
    
    @After
    public void deleteUserTest(){
	userDao.deleteUser(user);
    }
}
