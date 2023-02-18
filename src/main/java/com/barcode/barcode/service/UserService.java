package com.barcode.barcode.service;

import com.barcode.barcode.model.Order;

public interface UserService {
    String save(Order order);

    Order getUserName(String id);
}
