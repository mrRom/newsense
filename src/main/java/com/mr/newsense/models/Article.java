package com.mr.newsense.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class Article extends Model{
    private static final long serialVersionUID = -4231739104425789372L;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String url;
    @Column
    private String autor;
    @Column
    private String description;
    @Column(name = "publish_date", nullable = false, columnDefinition="DATETIME")
    private Date publishDate;
    
    public Article() {
	super();
    }

    public Article(Long id) {
	super(id);
    }

    public Article(String title, String url, String autor, String description,
	    Date publishDate) {
	super();
	this.title = title;
	this.url = url;
	this.autor = autor;
	this.description = description;
	this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	Article other = (Article) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }


    @Override
    public String toString() {
	return "Article [title=" + title + ", url=" + url
		+ ", autor=" + autor + ", description=" + description
		+ ", publishDate=" + publishDate + "]";
    }
}
