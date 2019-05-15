package com.myRetail.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

	private Description product_description;

	public Item() {

	}

	public Item(Description product_description) {
		this.product_description = product_description;
	}

	public Description getProduct_description() {
		return product_description;
	}

	public void setProduct_description(Description product_description) {
		this.product_description = product_description;
	}

}
