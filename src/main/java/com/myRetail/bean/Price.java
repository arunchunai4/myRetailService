package com.myRetail.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * Price bean
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

	@NotNull
	@Min(value = 1)
	@Max(value = 999999999)
	@Type(value = Integer.class)
	private int id;

	@NotNull
	private PriceValue priceValue;

	@Size(min = 1, max = 10, message = "last change user id should have characters of size between 1 and 10")
	@NotEmpty
	@NotNull
	private String lastChangeUser;

	private String name;

	public Price(@NotNull @Min(1) @Max(999999999) int id, @NotNull PriceValue priceValue,
			@Size(min = 1, max = 10, message = "last change user id should have characters of size between 1 and 10") @NotEmpty @NotNull String lastChangeUser,
			String name) {
		super();
		this.id = id;
		this.priceValue = priceValue;
		this.lastChangeUser = lastChangeUser;
		this.name = name;
	}

	public Price() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastChangeUser() {
		return lastChangeUser;
	}

	public void setLastChangeUser(String lastChangeUser) {
		this.lastChangeUser = lastChangeUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PriceValue getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(PriceValue priceValue) {
		this.priceValue = priceValue;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", " + priceValue.toString() + ", lastChangeUser=" + lastChangeUser + "]";
	}

}
