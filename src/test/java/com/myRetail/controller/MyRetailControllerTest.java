package com.myRetail.controller;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.myRetail.bean.Price;
import com.myRetail.bean.PriceValue;
import com.myRetail.service.MyRetailService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MyRetailController.class)
public class MyRetailControllerTest {

	private static Logger LOGGER = LoggerFactory.getLogger(MyRetailControllerTest.class);

	@Autowired
	MockMvc mockMvc;

	@MockBean
	MyRetailService myRetailService;

	PriceValue mockPriceBean = new PriceValue(1.25, "USD");
	Price mockPrice = new Price(123, mockPriceBean, "USER", "TITLE-TEST");

	@Test
	public void retrievePrice() throws Exception {
		Mockito.when(myRetailService.retrievePrice(Mockito.anyInt())).thenReturn(Optional.of(mockPrice));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/price/123")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("retrievePrice Result is :: " + result.getResponse().getStatus());

		String expectedValue = "{\"id\":123,\"priceValue\":{\"price\":1.25,\"currencyCode\":\"USD\"},\"lastChangeUser\":\"USER\",\"name\":\"TITLE-TEST\"}";
		JSONAssert.assertEquals(expectedValue, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void createPrice() throws Exception {
		String jsonContent = "{\"id\":123,\"priceValue\":{\"price\":1.25,\"currencyCode\":\"USD\"},\"lastChangeUser\":\"USER\",\"name\":\"TITLE-TEST\"}";

		Mockito.when(myRetailService.createPrice(Mockito.any(Price.class))).thenReturn(Mockito.anyBoolean());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/myretail/price").accept(MediaType.APPLICATION_JSON)
				.content(jsonContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		LOGGER.info("createPrice Result is :: " + response.getStatus());

		assertTrue(response.getStatus() == HttpStatus.OK.value());
	}

	@Test
	public void retrieveProductNotAvailable() throws Exception {
		Mockito.when(myRetailService.retrievePrice(Mockito.anyInt())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/price/123")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		LOGGER.info("retriveProductNotAvailable Result is :: " + response.getStatus());

		assertTrue(response.getStatus() == HttpStatus.NO_CONTENT.value());
	}

	@Test
	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void retriveTitleNotAvailable() throws Exception {
		mockPrice.setName(null);
		Mockito.when(myRetailService.retrievePrice(Mockito.anyInt())).thenThrow(HttpClientErrorException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/price/123")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		LOGGER.info("retriveTitleNotAvailable Result is :: " + response.getStatus());

		assertTrue(response.getStatus() == HttpStatus.BAD_REQUEST.value());

	}
}
