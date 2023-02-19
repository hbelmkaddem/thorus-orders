package com.barcode.barcode.service;

import com.barcode.barcode.model.Orders;

public interface OrderService {
    String save(Orders orders);

    Orders getUserName(String id);

    Orders findById(String orderId);

    Orders update(Orders orders);
}
