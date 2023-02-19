package com.barcode.barcode.service.impl;

import com.barcode.barcode.Repository.OrderRepository;
import com.barcode.barcode.model.Orders;
import com.barcode.barcode.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public String save(Orders user) {
        Orders user1 = orderRepository.save(user);
        if(Objects.nonNull(user1))
            return user1.getId();
        return "";
    }

    @Override
    public Orders getUserName(String id) {
        Optional<Orders> user = orderRepository.findById(id);
        if(user.isPresent())
            return user.get();
        return new Orders();
    }

    @Override
    public Orders findById(String orderId) {
        Optional<Orders> order = orderRepository.findById(orderId);
        if(order.isPresent())
            return order.get();
        return new Orders();
    }

    @Override
    public Orders update(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }
}
