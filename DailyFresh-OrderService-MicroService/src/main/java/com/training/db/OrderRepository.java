package com.training.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.model.Order;

public interface OrderRepository extends JpaRepository <Order,Integer>{

}
