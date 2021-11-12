package com.devsuperior.dscatalog.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.devsuperior.dscatalog.entities.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	
	@Autowired
	ProductService service;
	
	@Autowired
	ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Integer countProducts;
	private ProductDTO productDTO;
		
	@BeforeEach
	void setUp() throws Exception {
		productDTO = Factory.createProductDTO();
		existingId = 1L;
		nonExistingId= 1000L;
		countProducts = 25;
	}
	
	@Test
	public void deleteShouldDeleteWhenIdExists() {
		service.delete(existingId);
		
		Assertions.assertEquals(countProducts-1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist () {
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPageZeroSizeTen() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page <ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist () {
		PageRequest pageRequest = PageRequest.of(1000, 10);
		Page <ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName () {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());

	}

}
