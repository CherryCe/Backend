package com.ecommerce.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String password;

	@Column(unique = true)
	private String email;

	private String address;

	private String about;

	private String gender;

	private String phone;

	private Date date;

	private boolean active;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	@OneToOne(mappedBy = "user")
	private Cart cart;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> collect = roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName()))
				.collect(Collectors.toList());
		return collect;
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
}
