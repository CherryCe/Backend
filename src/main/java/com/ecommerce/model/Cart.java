package com.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> cartItems = new HashSet<>();

	@OneToOne
	private User user;
}
