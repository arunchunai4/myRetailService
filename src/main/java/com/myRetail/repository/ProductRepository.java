package com.myRetail.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.myRetail.model.Product;

/**
 * Product table repository
 * @author Arunchunai vendan Pugalenthi
 *
 */

@Repository
public interface ProductRepository extends CassandraRepository<Product, Integer> {
	
}
