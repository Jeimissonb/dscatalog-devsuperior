package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.entities.dto.ProductDTO;

public class Factory {

	public static Product createProduct () {
		Product product = new Product("Computer", "Gamer computer", 800.0, "https://lolesports.com/rewards");
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO () {
		Product product = createProduct();
		ProductDTO productDTO = new ProductDTO(product, product.getCategories());
		return productDTO; 
		
	}
	
	public static Category createCategory () {
		Category category = new Category (2L, "Electronics");
		return category;
	}
}
