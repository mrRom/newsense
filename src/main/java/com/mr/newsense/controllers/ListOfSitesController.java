package com.mr.newsense.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mr.newsense.dao.LinkDao;
import com.mr.newsense.dao.SourceDao;
import com.mr.newsense.dao.UserDao;
import com.mr.newsense.models.Source;
import com.mr.newsense.models.User;

@RestController
public class ListOfSitesController {
    private static final Logger log = LoggerFactory.getLogger(ListOfSitesController.class);
    @Autowired
    LinkDao linkDao;
    @Autowired
    UserDao userDao;
    @Autowired
    SourceDao sourceDao;
    
    
    @RequestMapping(value = "/listOfSites", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllSites(){
	List<String> sites = new ArrayList<String>();
	List<Source> links = (List<Source>) linkDao.findAll();
	for (Source link : links) {
	    sites.add(link.getHost());
	}
	if(sites.isEmpty()){
            return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
        }
	return new ResponseEntity<List<String>>(sites, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/selectedSites", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllSelectedSites(){
	final String username = SecurityContextHolder.getContext()
		.getAuthentication().getName();
	List<String> sites = new ArrayList<String>();
	Set<Source> sources = userDao.getUserByName(username).getSources();
	for (Source s: sources){
	    sites.add(s.getHost());
	}
	return new ResponseEntity<List<String>>(sites, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/selectedSites", method = RequestMethod.POST)
    public ResponseEntity<String> selectedSites(@RequestBody List<String> sites){
	final String username = SecurityContextHolder.getContext()
		.getAuthentication().getName();
	log.info(username + "selected:" + sites);
	User user = userDao.getUserByName(username);
	user.setSources(sourceDao.selectSourcesByHostNames(sites));
	userDao.updateUser(user);
	return new ResponseEntity<String>(HttpStatus.OK);
    }
}
