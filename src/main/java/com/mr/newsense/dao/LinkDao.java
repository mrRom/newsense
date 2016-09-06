package com.mr.newsense.dao;

import org.springframework.data.repository.CrudRepository;

import com.mr.newsense.models.Link;

public interface LinkDao extends CrudRepository<Link, Long> {

}
