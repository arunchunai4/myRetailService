package com.myRetail.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.myRetail.bean.Price;
import com.myRetail.bean.PriceValue;
import com.myRetail.exception.MyRetailException;
import com.myRetail.external.ProductTitleExternal;
import com.myRetail.model.Product;
import com.myRetail.repository.ProductRepository;

@Service
public class MyRetailServiceImpl implements MyRetailService {

	private static Logger LOGGER = LoggerFactory.getLogger(MyRetailServiceImpl.class);

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductTitleExternal prodTitleExt;

	@Override
	public void createPrice(Price price) throws MyRetailException {

		Product prod = new Product();
		prod.setId(price.getId());
		prod.setPrice(price.getPriceValue().getPrice());
		prod.setCurrency_code(price.getPriceValue().getCurrencyCode());
		prod.setLast_change_user(price.getLastChangeUser());
		prod.setLast_update_ts(LocalDateTime.now());

		try {
			productRepo.save(prod);
		}

		catch (Exception e) {
			LOGGER.error(e.getMessage() + " :: " + price.toString(), e);
			throw new MyRetailException(e.getMessage() + " :: " + price.toString(), e);
		}
	}

	@Override
	public Optional<Price> retrievePrice(Integer productId) throws MyRetailException {

		Price price = new Price();
		PriceValue priceValue = new PriceValue();
		Optional<Price> responsePrice;
		Optional<Product> responseProduct;
		String title = null;

		try {

			responseProduct = productRepo.findById(productId);

			if (responseProduct.isPresent()) {
				price.setId(responseProduct.get().getId());
				priceValue.setPrice(responseProduct.get().getPrice());
				priceValue.setCurrencyCode(responseProduct.get().getCurrency_code());
				price.setPriceValue(priceValue);
				price.setLastChangeUser(responseProduct.get().getLast_change_user());

				title = prodTitleExt.getTitle(price.getId());

				if (null != title && !title.isEmpty()) {
					price.setName(title);
				} else {
					LOGGER.info("Title is null for the product :: " + productId);
					throw new MyRetailException("Title is null for the product :: " + productId);
				}
				responsePrice = Optional.of(price);
				return responsePrice;
			} else {
				return Optional.empty();
			}

		}

		catch (HttpClientErrorException e) {
			throw e;
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage() + " :: " + productId, e);
			throw new MyRetailException(e.getMessage() + " :: procuctId" + productId, e);
		}
	}

}
