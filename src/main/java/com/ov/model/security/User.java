package com.ov.model.security;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {
	
	
	@Id
	@Column(name = "USERNAME", length = 20)
	@Size(min = 5, max = 20)
	private String username;
	
	@Column(name = "PASSWORD")
	@NotNull
	@Size(min = 5, max = 20)
	private String password;
	
    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;
	
    @Column(name = "LASTPASSWORDRESETTIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordRestTime;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "USER_AUTHORITY",
    		joinColumns = @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME"),
    		inverseJoinColumns = @JoinColumn(name = "AUTHORITY", referencedColumnName = "NAME")
    		)
    private List<Authority> authorities;

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastPasswordRestTime() {
		return lastPasswordRestTime;
	}

	public void setLastPasswordRestTime(Date lastPasswordRestTime) {
		this.lastPasswordRestTime = lastPasswordRestTime;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
}