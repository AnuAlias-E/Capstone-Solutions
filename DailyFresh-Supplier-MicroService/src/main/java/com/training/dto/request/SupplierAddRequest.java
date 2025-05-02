package com.training.dto.request;

import com.training.model.Supplier;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierAddRequest {
Supplier supplier ;

public Supplier getSupplier() {
	return supplier;
}

public void setSupplier(Supplier supplier) {
	this.supplier = supplier;
}

}
