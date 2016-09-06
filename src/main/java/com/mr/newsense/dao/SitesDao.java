package com.mr.newsense.dao;

import java.util.List;

public interface SitesDao {
    public List<String> getAllSelectedByUserSites(String userName);
    public void createUpdateSelectedUserSites(String userName, List<String> sites);
}
