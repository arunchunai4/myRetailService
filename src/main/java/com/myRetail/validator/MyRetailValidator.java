package com.myRetail.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.myRetail.bean.Price;
import com.myRetail.bean.PriceValue;

/**
 * Price Validator
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */

public class MyRetailValidator {

	public static String validateBeans(Price price) {

		StringBuilder validationError = new StringBuilder();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Price>> priceViolations = validator.validate(price);
		Set<ConstraintViolation<PriceValue>> priceBeanViolations = validator.validate(price.getPriceValue());
		priceViolations.stream()
				.forEach(v -> validationError.append(v.getPropertyPath() + " " + v.getMessage() + "\n"));
		priceBeanViolations.stream()
				.forEach(v -> validationError.append(v.getPropertyPath() + " " + v.getMessage() + "\n"));
		String idError = validateInteger(price.getId());
		if (null != idError && !idError.isEmpty()) {
			validationError.append(idError);
		}
		return validationError.toString();

	}

	private static String validateInteger(Integer id) {
		try {
			Integer.valueOf(id);
		} catch (NumberFormatException e) {
			return "id should be a valid integer and greter than 0";
		}
		return null;
	}
}
