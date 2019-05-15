package com.myRetail.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "product")
public class Product {

	@PrimaryKey
	private Integer id;
	private Double price;
	private String currency_code;
	private LocalDateTime last_update_ts;
	private String last_change_user;

	public Product() {
	}

	public Product(Integer id, Double price, String currency_code, LocalDateTime last_update_ts,
			String last_change_user) {
		super();
		this.id = id;
		this.price = price;
		this.currency_code = currency_code;
		this.last_update_ts = last_update_ts;
		this.last_change_user = last_change_user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public LocalDateTime getLast_update_ts() {
		return last_update_ts;
	}

	public void setLast_update_ts(LocalDateTime last_update_ts) {
		this.last_update_ts = last_update_ts;
	}

	public String getLast_change_user() {
		return last_change_user;
	}

	public void setLast_change_user(String last_change_user) {
		this.last_change_user = last_change_user;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", currency_code=" + currency_code + ", last_change_user="
				+ last_change_user + "]";
	}

}
