package com.myRetail.external;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.myRetail.exception.MyRetailException;

/**
 * Get title from external service
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */
public interface ProductTitleExternal {

	/**
	 * Get product title
	 * 
	 * @param productId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws MyRetailException
	 */
	public String getTitle(Integer productId) throws JsonParseException, JsonMappingException, IOException;
}
