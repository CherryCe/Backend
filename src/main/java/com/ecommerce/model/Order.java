package com.ecommerce.model;

import java.util.Date;
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
@Table(name = "orden")
@Getter
@Setter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String status;

	private String payment;

	private Date delivered;

	private double amt;

	private String billing;

	private Date createAt;

	@OneToOne
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItem> orderItems = new HashSet<>();
}
