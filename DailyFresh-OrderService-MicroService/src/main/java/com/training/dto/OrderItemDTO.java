package com.training.dto;

import java.util.List;

public class OrderItemDTO {

	private int orderItemId;
	private String itemName;
	private int quantity;
	private double price;
	private List<OrderItemDTO> orderItemDTOs;
	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<OrderItemDTO> getOrderItemDTOs() {
		return orderItemDTOs;
	}

	public void setOrderItemDTOs(List<OrderItemDTO> orderItemDTOs) {
		this.orderItemDTOs = orderItemDTOs;
	}


}
