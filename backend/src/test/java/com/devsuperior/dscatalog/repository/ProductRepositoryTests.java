package com.devsuperior.dscatalog.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired
	ProductRepository repository;
	
	private Long existId;
	private Long notExistId;
	private Long countTotalIds;
	
	@BeforeEach
	public void setUp() throws Exception {
		existId = 1L;
		notExistId = 0L;
		countTotalIds = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull () {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalIds+1, product.getId());
	}
	
	@Test
	public void findByIdShouldReturnObjectOptionalNotEmptyWhenIdExists () {
		
		Optional <Product> object = repository.findById(existId);
		Assertions.assertTrue(object.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnObjectOptionalEmptyWhenIdDoesNotExists () {
		
		Optional <Product> object = repository.findById(notExistId);
		Assertions.assertTrue(object.isEmpty());
	}
	
	
	
	@Test
	public void deleteShouldEraseObjectWhenIdExists() {

		repository.deleteById(existId);
		Optional<Product> object  = repository.findById(existId);
		
		Assertions.assertFalse(object.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAcessExceptionWhenIdDoesNotExists() {
		

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(notExistId);
		} );
		
	}
}


