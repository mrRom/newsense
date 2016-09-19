package com.mr.newsense.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends Model{

    private static final long serialVersionUID = 3074119656569223847L;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column (nullable = false)
    private int enabled;
    
    @ManyToMany (fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "selected_sources", joinColumns = {
		@JoinColumn(name = "user_id", nullable = false) },
		inverseJoinColumns = { @JoinColumn(name = "source_id",
				nullable = false)})
    private Set<Source> sources = new HashSet<>();

    public User() {
	super();
    }
    
    public User(Long id) {
	super(id);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getEnabled() {
        return enabled;
    }
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    
    public Set<Source> getSources() {
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
	return "User [username=" + username + ", password="
		+ password + ", enabled=" + enabled + "]";
    }
}
