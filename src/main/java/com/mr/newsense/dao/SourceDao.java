package com.mr.newsense.dao;

import java.util.List;
import java.util.Set;

import com.mr.newsense.models.Source;

public interface SourceDao {
    Set<Source> selectSourcesByHostNames(List<String> hosts);
}
