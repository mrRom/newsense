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

import com.mr.newsense.TestUtils;
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
    
    TestUtils tu = new TestUtils();
    User user = tu.createBasicTestUser(); 
    Source source = tu.createTestSource("test1", "test1/test1");
    
    @Before
    public void createUserTest(){
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
	User myuser = userDao.getUserByName("test");
	Source anotherSource = tu.createTestSource("test2", "test2/test2");
	myuser.getSources().add(anotherSource);
	userDao.updateUser(myuser);
	User updateduser = userDao.getUserByName("test");
	assertTrue(updateduser.getSources().contains(anotherSource));
    }
    
    @After
    public void deleteUserTest(){
	userDao.deleteUser(user);
    }
}
