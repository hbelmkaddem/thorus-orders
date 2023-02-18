package com.barcode.barcode.service.impl;

import com.barcode.barcode.Repository.OrderRepository;
import com.barcode.barcode.model.Order;
import com.barcode.barcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public String save(Order user) {
        Order user1 = orderRepository.save(user);
        if(Objects.nonNull(user1))
            return user1.getId();
        return "";
    }

    @Override
    public Order getUserName(String id) {
        Optional<Order> user = orderRepository.findById(id);
        if(user.isPresent())
            return user.get();
        return new Order();
    }
}
