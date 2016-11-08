package com.mr.newsense.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_roles",
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "user_id" }))
public class UserRole extends Model{
    private static final long serialVersionUID = 1967973492840525173L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "role", nullable = false, length = 45)
    private String role;
    
    public UserRole() {
	super();
    }

    public UserRole(Long id) {
	super(id);
    }

    public UserRole(User user, String role) {
	super();
	this.user = user;
	this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
