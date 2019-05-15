package com.myRetail.controller;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.bean.Price;
import com.myRetail.exception.MyRetailException;
import com.myRetail.service.MyRetailService;
import com.myRetail.validator.MyRetailValidator;

/**
 * myRetail controller class managing RESTFul calls
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */

@RestController
@RequestMapping(value = "/myretail")

public class MyRetailController {

	private static Logger LOGGER = LoggerFactory.getLogger(MyRetailController.class);

	@Autowired
	private MyRetailService myRetailService;

	/**
	 * create price
	 * 
	 * @param price
	 * @return
	 */

	@PutMapping(value = "/price")
	public ResponseEntity<String> createPrice(@RequestBody Price price) {

		String validationError = null;
		validationError = MyRetailValidator.validateBeans(price);

		if(null != validationError && validationError.length() >0) {
			return new ResponseEntity<>("Validation Error :: " + validationError , HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("Creating price :: " + price.toString());

		try {

			myRetailService.createPrice(price);

			LOGGER.info("Created price :: " + price.toString());
		} catch (MyRetailException e) {
			return new ResponseEntity<>(e.getMessage() + e, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage() + e, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Created price :: " + price.toString(), HttpStatus.OK);
	}

	/**
	 * retrieve price
	 * 
	 * @param productId
	 * @return
	 */

	@GetMapping(value = "/price/{productId}")
	public ResponseEntity<String> retreivePrice(@PathVariable Integer productId) throws JsonProcessingException {
		StringBuilder response = new StringBuilder();
		ObjectMapper mapper = new ObjectMapper();
		Optional<Price> price;
		LOGGER.info("Retrieving price for the product:: " + productId);
		try {

			price = myRetailService.retrievePrice(productId);
			price.ifPresentOrElse(p -> {
				try {
					response.append(mapper.writeValueAsString(p));
				} catch (JsonProcessingException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}, () -> response.append("Product not found"));

		}

		catch (MyRetailException e) {
			return new ResponseEntity<>(e.getMessage() + e, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage() + e, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

}
