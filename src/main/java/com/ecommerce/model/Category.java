package com.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();

}
