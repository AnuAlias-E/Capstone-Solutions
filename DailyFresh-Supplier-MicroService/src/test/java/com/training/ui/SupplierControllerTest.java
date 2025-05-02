//package com.training.ui;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.training.dto.request.SupplierAddRequest;
//import com.training.dto.request.SupplierUpdateRequest;
//import com.training.dto.response.SupplierAddResponse;
//import com.training.dto.response.SupplierUpdateResponse;
//import com.training.model.City;
//import com.training.model.Contact;
//import com.training.model.Supplier;
//import com.training.service.SupplierService;
//
//@ExtendWith(MockitoExtension.class)
//public class SupplierControllerTest {
//	@InjectMocks
//	private SupplierController supplierController;
//	@Mock
//	private SupplierService service;
//
//	private ObjectMapper objectMapper;
//	private MockMvc mockMvc;
//	City city;
//	Contact contact;
//
//	@BeforeEach
//	void setUp() {
//		city = new City(1, "Pune");
//		contact = new Contact(1, "Madhav", "1234567874", "madhav@gmail.com");
//		objectMapper = new ObjectMapper();
//		mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
//
//	}
//
//@Test
//void testAddNewSupplier() throws Exception
//	{
//		SupplierAddRequest request = new SupplierAddRequest();
//
//		request.setSupplier(new Supplier(1, "DailyFreshPune", city, contact));
//
//		Supplier supplier = new Supplier(1, "DailyFreshPune", city, contact);
//		
//		SupplierAddResponse response = new SupplierAddResponse();
//		response.setStatusCode(201);
//		response.setDescription("Supplier Added Successfully");
//		response.setSupplier(supplier);
//		
//		Mockito.when(service.addNewSupplier(any(Supplier.class))).thenReturn(supplier);
//		mockMvc.perform(post("/api/add")
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON)
//		.content(objectMapper.writeValueAsString(request)))
//		.andDo(print())
//		.andExpect(status().isCreated())
//		
//		.andExpect(jsonPath("$.statusCode").value(201))
//		.andExpect(jsonPath("$.description").value("Supplier Added Successfully"))
//		.andExpect(jsonPath("$.supplier.supplierId").value(1))
//		.andExpect(jsonPath("$.supplier.supplierName").value("DailyFreshPune"))
//		.andExpect(jsonPath("$.supplier.city.id").value(1))
//		.andExpect(jsonPath("$.supplier.city.name").value("Pune"))
//		.andExpect(jsonPath("$.supplier.contact.id").value(1))
//		.andExpect(jsonPath("$.supplier.contact.name").value("Madhav"))
//		.andExpect(jsonPath("$.supplier.contact.phone").value("1234567874"))
//		.andExpect(jsonPath("$.supplier.contact.email").value("madhav@gmail.com"))
//		
//		
//		
//		;
//	}	
//
//@Test
//void testUpdateSupplier() throws Exception
//	{
////		SupplierUpdateRequest request = new SupplierUpdateRequest();
////
////		Supplier supplierToUpdate = new Supplier(1, "DailyFreshPune", city, contact);
////		request.setSupplier(supplierToUpdate);
////
////		Supplier supplierUpdated = new Supplier(1, "DailyFreshPune", city, contact);
////		
////		
////		Mockito.when(service.updateSupplier(any(Supplier.class))).thenReturn(supplierUpdated);
////		SupplierUpdateResponse response = new SupplierUpdateResponse();
////		response.setStatusCode(200);
////		response.setDescription("Supplier Modified Successfully");
////		response.setSupplier(supplierUpdated);
////		mockMvc.perform(put("/api/modify")
////		.contentType(MediaType.APPLICATION_JSON)
////		.accept(MediaType.APPLICATION_JSON)
////		.content(objectMapper.writeValueAsString(request)))
////		.andDo(print())
////		.andExpect(status().isOk())
////		
////		.andExpect(jsonPath("$.statusCode").value(200))
////		.andExpect(jsonPath("$.description").value("Supplier Modified Successfully"))
////		.andExpect(jsonPath("$.supplier.supplierId").value(1))
////		.andExpect(jsonPath("$.supplier.supplierName").value("DailyFreshPune"))
////		.andExpect(jsonPath("$.supplier.city.id").value(1))
////		.andExpect(jsonPath("$.supplier.city.name").value("Pune"))
////		.andExpect(jsonPath("$.supplier.contact.id").value(1))
////		.andExpect(jsonPath("$.supplier.contact.name").value("Madhav"))
////		.andExpect(jsonPath("$.supplier.contact.phone").value("1234567874"))
////		.andExpect(jsonPath("$.supplier.contact.email").value("madhav@gmail.com"))
////		
//		
//		
//		;
//	}	
//
//}
