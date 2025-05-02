package com.training.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.training.db.OrderRepository;
import com.training.dto.BillOrderDTO;
import com.training.dto.CustomerOrderDTO;
import com.training.dto.CustomerOrderItemDTO;
import com.training.dto.OrderBillDTO;
import com.training.dto.OrderBillItemDTO;
import com.training.dto.OrderDTO;
import com.training.dto.OrderItemDTO;
import com.training.dto.request.BillAddRequest;
import com.training.dto.request.OrderAddRequest;
import com.training.dto.request.StockUpdateRequest;
import com.training.model.Order;
import com.training.model.OrderItem;

@Service
public class OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderRepository orderRepository;

	public OrderDTO placeOrder(OrderAddRequest orderRequest) {
		double totalAmount = 0.0;
		CustomerOrderDTO customerOrderDTO = orderRequest.getCustomerOrderDTO();
		Order order = new Order();
		order.setOrderDate(customerOrderDTO.getOrderDate());
		order.setDeliveryLocation(customerOrderDTO.getDeliveryLocation());
		List<OrderItem> orderItems = new ArrayList<>();

		List<CustomerOrderItemDTO> customerOrderItems = customerOrderDTO.getCustomerOrderItemDTOs();
		System.out.println(customerOrderItems);
		if (customerOrderItems != null && !customerOrderItems.isEmpty()) {
			for (CustomerOrderItemDTO customerOrderItemDTO : customerOrderItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setItemName(customerOrderItemDTO.getItemName());
				orderItem.setPrice(customerOrderItemDTO.getPrice());
				orderItem.setQuantity(customerOrderItemDTO.getQuantity());

				orderItems.add(orderItem);
			}

			order.setOrderItems(orderItems);
			for (OrderItem item : order.getOrderItems()) {

				totalAmount += item.getQuantity() * item.getPrice();
			}

			order.setOrderDate(LocalDate.now());
			order.setOrderAmount(totalAmount);
			Order savedOrder = orderRepository.save(order);

			OrderDTO orderDTO = new OrderDTO();

			if (savedOrder != null) {
				String billServiceUrl = "http://localhost:8085/api/addBill"; // replace with actual
				OrderBillDTO orderBillDTO = new OrderBillDTO();

				orderBillDTO.setBillDate(savedOrder.getOrderDate());
				orderBillDTO.setCustomerId(customerOrderDTO.getCustomerId());
				orderBillDTO.setTotalAmount(totalAmount);
				List<OrderBillItemDTO> orderBillItemDTOs = new ArrayList<>();

				for (OrderItem orderItem : savedOrder.getOrderItems()) {
					OrderBillItemDTO orderBillItemDTO = new OrderBillItemDTO();
					orderBillItemDTO.setItemName(orderItem.getItemName());
					orderBillItemDTO.setPrice(orderItem.getPrice());
					orderBillItemDTO.setQuantity(orderItem.getQuantity());
					orderBillItemDTOs.add(orderBillItemDTO);

					String stockServiceUrl = "http://localhost:8084/api/stock/update"; // replace with actual
					StockUpdateRequest stockUpdateRequest = new StockUpdateRequest();
					stockUpdateRequest.setItemName(orderItem.getItemName());
					stockUpdateRequest.setQuantity(orderItem.getQuantity());
					stockUpdateRequest.setLocationName(order.getDeliveryLocation());
					restTemplate.put(stockServiceUrl, stockUpdateRequest, void.class);

				}
				orderBillDTO.setOrderBillItemDTOs(orderBillItemDTOs);

				BillAddRequest billAddRequest = new BillAddRequest();
				billAddRequest.setOrderBillDTO(orderBillDTO);

	            // Use ResponseEntity<String> to handle unexpected responses like plain text
	            try {
	                ResponseEntity<String> message = restTemplate.postForEntity(billServiceUrl, billAddRequest, String.class);
	                
	                // Check if the response starts with < (XML), if not, log or handle error
	                String responseBody = message.getBody();
	                if (responseBody != null && responseBody.startsWith("<")) {
	                    System.out.println("Bill Added : " + responseBody);
	                } else {
	                    // Log or handle the unexpected non-XML response
	                    System.err.println("Unexpected response: " + responseBody);
	                    // Optionally, you can throw a custom exception to capture this error.
	                }
	            } catch (Exception e) {
	                System.err.println("Error adding bill: " + e.getMessage());
	                // Optionally, rethrow or handle specific exception types like HttpClientErrorException.
	            }
				
				orderDTO.setOrderId(order.getOrderId());
				orderDTO.setOrderAmount(order.getOrderAmount());
				orderDTO.setDeliveryLocation(order.getDeliveryLocation());
				orderDTO.setOrderDate(order.getOrderDate());
			List<	OrderItemDTO> itemDTOs = new ArrayList<OrderItemDTO>();
				for (OrderItem orderItem : order.getOrderItems()) {
					OrderItemDTO orderItemDTO = new OrderItemDTO();
					orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
					orderItemDTO.setItemName(orderItem.getItemName());
					orderItemDTO.setPrice(orderItem.getPrice());
					orderItemDTO.setQuantity(orderItem.getQuantity());
					itemDTOs.add(orderItemDTO);
				}
				orderDTO.setOrderItems(itemDTOs);
			} else {
				throw new DataIntegrityViolationException("Order is not saved");
			}
			
			return orderDTO;
		} else {
			// log warning or throw a more descriptive exception
			throw new IllegalArgumentException("Order must contain at least one item.");
		}

	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
}
