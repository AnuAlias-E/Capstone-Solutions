package com.training.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.dto.request.SupplierAddRequest;
import com.training.dto.request.SupplierUpdateRequest;
import com.training.dto.response.SupplierAddResponse;
import com.training.dto.response.SupplierSearchResponse;
import com.training.dto.response.SupplierShowAllResponse;
import com.training.dto.response.SupplierUpdateResponse;
import com.training.exception.SupplierNotFoundException;
import com.training.model.Supplier;
import com.training.service.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SupplierController {
	
	@Autowired
	SupplierService service;
	
	@PostMapping(value = "/add", consumes=MediaType.APPLICATION_JSON_VALUE )	
	public ResponseEntity<SupplierAddResponse> addNewSupplier(@Valid @RequestBody SupplierAddRequest request){
		SupplierAddResponse response = new SupplierAddResponse();
		Supplier supplier =service.addNewSupplier(request.getSupplier());
		response.setStatusCode(201);
		response.setDescription("Supplier Added Successfully");
		response.setSupplier(supplier);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	@PutMapping(value = "/modify")
	public ResponseEntity<SupplierUpdateResponse> updateSupplier(@RequestBody SupplierUpdateRequest request) throws SupplierNotFoundException {
		SupplierUpdateResponse response = new SupplierUpdateResponse();
		
		Supplier supplier = service.searchSupplier(request.getSupplier().getSupplierId());
		if (supplier != null) {
		
			Supplier supplierResult = service.updateSupplier(request.getSupplier());
		response.setStatusCode(200);
		response.setDescription("Supplier Modified Successfully");
		response.setSupplier(supplierResult);
		return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			response.setStatusCode(404);
			response.setDescription("Supplier Not Found");
			response.setSupplier(supplier);			
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/find/{sid}")	
	public ResponseEntity<SupplierSearchResponse> searchSupplier(@PathVariable(name = "sid") int supplierId) throws Exception{
		Supplier supplier = service.searchSupplier(supplierId);
		SupplierSearchResponse response = new SupplierSearchResponse();
		if (supplier != null) {
			response.setStatusCode(200);
			response.setDescription("Supplier fetched Successfully");
			response.setSupplier(supplier);	
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			Exception e= new SupplierNotFoundException("Supplier Not Found with id:"+supplierId);
			throw e;
		}	
	}
	
	
	@GetMapping(value = "/showAll", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SupplierShowAllResponse> getAllSuppliers() throws SupplierNotFoundException {
		List<Supplier> suppliers = service.getAllSuppliers();
		SupplierShowAllResponse response = new SupplierShowAllResponse();
		response.setStatusCode(200);
		response.setDescription("All suppliers Fetched");
		response.setSuppliers(suppliers);

		return ResponseEntity.ok(response);
	}

}
