package com.myRetail.service;

import java.util.Optional;

import com.myRetail.bean.Price;
import com.myRetail.exception.MyRetailException;

/**
 * Service class managing all retail operations
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */

public interface MyRetailService {

	/**
	 * Create single price
	 * 
	 * @param price
	 * @return
	 * @throws MyRetailException
	 */
	public void createPrice(Price price) throws MyRetailException;

	/**
	 * Retrieve price for given product
	 * 
	 * @param productId
	 * @return
	 * @throws MyRetailException
	 */
	public Optional<Price> retrievePrice(Integer productId) throws MyRetailException;

}
