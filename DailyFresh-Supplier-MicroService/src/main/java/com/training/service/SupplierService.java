package com.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.db.SupplierRepository;
import com.training.exception.SupplierNotFoundException;
import com.training.model.Supplier;

@Service
public class SupplierService {

	@Autowired
	SupplierRepository suppRepo;
	

	
	public Supplier addNewSupplier(Supplier supplier) {
				return suppRepo.save(supplier);
	}

	public Supplier updateSupplier(Supplier supplier) {
		return suppRepo.save(supplier);
	}

	public Supplier searchSupplier(int supplierId) throws SupplierNotFoundException {
		return suppRepo.findById(supplierId).orElseThrow(
				() -> new SupplierNotFoundException("Supplier Not Found With Id: " + supplierId));
	}
	
	public List<Supplier> getAllSuppliers() throws SupplierNotFoundException{
		List<Supplier> suppliers = suppRepo.findAll();
		if(suppliers.isEmpty()) {
			throw new SupplierNotFoundException("Suppliers Not Found");
		}
		else {
			return suppliers;
		}
	}

}
