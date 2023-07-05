package com.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int quantity;

	private double total;

	@ManyToOne
	private Cart cart;

	@OneToOne
	private Product product;
}
