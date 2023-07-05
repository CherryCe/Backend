package com.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private double price;

	private boolean stock;

	private int quantity;

	private boolean live;

	private String image;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
}
