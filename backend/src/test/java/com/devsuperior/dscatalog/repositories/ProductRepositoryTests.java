package com.devsuperior.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExixtingId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExixtingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		assertNotNull(product.getId());
		assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void findByIdShouldReturnNomEmptyOptionalWhenIdExixts() {
		Optional<Product> result = repository.findById(existingId);
		
		assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExixts() {
		Optional<Product> result = repository.findById(nonExixtingId);
		
		assertTrue(result.isEmpty());;
	}

	@Test
	public void deleteShouldObjectWhenIdExists() {
		
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowExceptionWhenIdDoesNotExists() {
		
		assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExixtingId);
		});
	}
}
