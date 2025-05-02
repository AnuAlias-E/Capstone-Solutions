package com.training.dto.request;

import com.training.dto.CustomerOrderDTO;

public class OrderAddRequest {
	CustomerOrderDTO customerOrderDTO;

	public CustomerOrderDTO getCustomerOrderDTO() {
		return customerOrderDTO;
	}

	public void setCustomerOrderDTO(CustomerOrderDTO customerOrderDTO) {
		this.customerOrderDTO = customerOrderDTO;
	}   
    
    
}
