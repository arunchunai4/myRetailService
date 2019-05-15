package com.myRetail.bean;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PriceBean {

	@NotNull
	@DecimalMin("0")
	private Double price;

	@Size(min = 3, max = 3, message = "currency code should have 3 characters")
	@NotNull
	@NotEmpty
	private String currencyCode;

	public PriceBean() {
		
	}
	
	public PriceBean(@NotNull @DecimalMin("0") Double price,
			@Size(min = 3, max = 3, message = "currency code should have 3 characters") @NotNull @NotEmpty String currencyCode) {
		this.price = price;
		this.currencyCode = currencyCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return " [price=" + price + ", currencyCode=" + currencyCode + "]";
	}

}
