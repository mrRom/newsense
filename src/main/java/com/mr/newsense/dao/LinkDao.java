package com.mr.newsense.dao;

import org.springframework.data.repository.CrudRepository;

import com.mr.newsense.models.Source;

public interface LinkDao extends CrudRepository<Source, Long> {
    
}
