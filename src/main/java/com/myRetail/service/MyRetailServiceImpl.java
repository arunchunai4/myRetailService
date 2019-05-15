package com.myRetail.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myRetail.bean.Price;
import com.myRetail.bean.PriceBean;
import com.myRetail.exception.MyRetailException;
import com.myRetail.model.Product;
import com.myRetail.repository.ProductRepository;

@Service
public class MyRetailServiceImpl implements MyRetailService {

	private static Logger LOGGER = LoggerFactory.getLogger(MyRetailServiceImpl.class);

	@Autowired
	private ProductRepository productRepo;

	@Override
	public void createPrice(Price price) throws MyRetailException {

		Product prod = new Product();
		prod.setId(price.getId());
		prod.setPrice(price.getPriceBean().getPrice());
		prod.setCurrency_code(price.getPriceBean().getCurrencyCode());
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
		PriceBean priceBean = new PriceBean();
		Optional<Price> responsePrice;
		Optional<Product> responseProduct;

		try {
			responseProduct = productRepo.findById(productId);

			responseProduct.ifPresentOrElse(p -> {
				price.setId(p.getId());
				priceBean.setPrice(p.getPrice());
				priceBean.setCurrencyCode(p.getCurrency_code());
				price.setPriceBean(priceBean);
				price.setLastChangeUser(p.getLast_change_user());
			}, () -> LOGGER.info("Product not found"));
			responsePrice = Optional.of(price);

		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " :: " + productId, e);
			throw new MyRetailException(e.getMessage() + " :: " + productId, e);
		}
		return responsePrice;
	}

}
