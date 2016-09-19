package com.mr.newsense.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="sources")
public class Source extends Model{

    private static final long serialVersionUID = 4447727038104003771L;
    
    public Source() {
	super();
    }

    public Source(Long id) {
	super(id);
    }

    @Column(name = "linkvalue", unique = true, nullable = false)
    private String url;
    @Column(name = "host", unique = true, nullable = false)
    private String host;
    
    @ManyToMany(mappedBy="sources", fetch=FetchType.LAZY)
    private Set<User> users;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
	return "Source [url=" + url + ", host=" + host + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((host == null) ? 0 : host.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Source other = (Source) obj;
	if (host == null) {
	    if (other.host != null)
		return false;
	} else if (!host.equals(other.host))
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }
}
