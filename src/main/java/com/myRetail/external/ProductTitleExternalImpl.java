package com.myRetail.external;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.bean.RedSky;

@Service
public class ProductTitleExternalImpl implements ProductTitleExternal {

	private static Logger LOGGER = LoggerFactory.getLogger(ProductTitleExternalImpl.class);

	@Autowired
	private Environment env;

	public String getTitle(Integer productId) throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		RedSky redSky = new RedSky();
		ResponseEntity<String> response = null;

		LOGGER.info("Retrieving title for the product :: " + productId);
		String jsonURL = env.getProperty("redsky.url.first") + productId + env.getProperty("redsky.url.last");
		LOGGER.info("Calling Red Sky Service :: " + jsonURL);

		try {
			response = restTemplate.exchange(jsonURL, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
			});
			LOGGER.info("Response code for :: " + jsonURL + " is :: " + response.getStatusCode());
			redSky = mapper.readValue(response.getBody(), RedSky.class);

		} catch (final HttpClientErrorException e) {
			LOGGER.info("Response code for :: " + jsonURL + " is :: " + e.getStatusCode());
			if (e.getStatusCode().toString().contains("404")) {
				throw e;
			}
		}
		return redSky.getProduct().getItem().getProduct_description().getTitle();
	}
}
