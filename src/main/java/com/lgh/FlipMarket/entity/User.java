package com.lgh.FlipMarket.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@Column(nullable = false)
	private String email;
	
	private String pwd;
	
	@Column(nullable = false)
	private String name;
	
	private String provider;
	
	private String providerId;
	
	private String role;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String email, String pwd, String name, String provider, String providerId) {
		this.email = email;
		this.pwd = pwd;
		this.name = name;
		this.provider = provider;
		this.providerId = providerId;
	}
	
	public User(String email, String pwd, String name, String provider, String providerId, String role) {
		this.email = email;
		this.pwd = pwd;
		this.name = name;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
	}
	
	public Long getNum() {
		return num;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}
	
	public String getName() {
		return name;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public String getProviderId() {
		return providerId;
	}

	public String getRole() {
		return role;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
	
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public void setPwd(String pwd) {
    	this.pwd = pwd;
    }
    
}
