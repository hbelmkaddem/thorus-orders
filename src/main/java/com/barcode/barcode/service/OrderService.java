package com.barcode.barcode.service;

import com.barcode.barcode.model.Orders;

import java.util.List;

public interface OrderService {
    String save(Orders orders);

    Orders getUserName(String id);

    Orders findById(String orderId);

    Orders update(Orders orders);

    List<Orders> findAll();
}
