package com.devsuperior.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.entities.dto.ProductDTO;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
		
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Integer countElements;

	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countElements = 25;
	}
	
	@Test
	public void findAllShouldReturnPageSortedWhenSortByName () throws Exception {
		ResultActions res = mockMvc.perform(get("/products?page=0&size=12&sort=name,asc").accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isOk());
		res.andExpect(jsonPath("$.content").exists());
		res.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		res.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		res.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));

	}
	

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
	
	   ProductDTO productDTO = Factory.createProductDTO();
	   
	   String name = productDTO.getName();
	   String description = productDTO.getDescription();
	   

	
	   String jsonObject = objectMapper.writeValueAsString(productDTO);
	   ResultActions res = mockMvc.perform(put("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON)
			   				.content(jsonObject).contentType(MediaType.APPLICATION_JSON));
	   
	   res.andExpect(status().isOk());
	   res.andExpect(jsonPath("$.id").value(existingId));
	   res.andExpect(jsonPath("$.name").value(name));
	   res.andExpect(jsonPath("$.description").value(description));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
	
	   ProductDTO productDTO = Factory.createProductDTO();
	
	   String jsonObject = objectMapper.writeValueAsString(productDTO);
	   ResultActions res = mockMvc.perform(put("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON)
			   				.content(jsonObject).contentType(MediaType.APPLICATION_JSON));
	   
	   res.andExpect(status().isNotFound());

    }
}
