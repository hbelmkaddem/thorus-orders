package com.barcode.barcode.service.impl;

import com.barcode.barcode.Repository.OrderRepository;
import com.barcode.barcode.model.Orders;
import com.barcode.barcode.model.Etats;
import com.barcode.barcode.service.OrderService;
import com.barcode.barcode.service.StateService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private StateService stateService;

    public OrderServiceImpl(OrderRepository orderRepository, StateService stateService) {
        this.orderRepository = orderRepository;
        this.stateService = stateService;
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

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateState(Orders orders) {
        List<Etats> states= stateService.findAll();
        List<Etats> orderedStates = states.stream().sorted(Comparator.comparing(Etats::getOrdre)).collect(Collectors.toList());
        Optional<Etats> optionalState = orderedStates.stream().filter(state -> state.getEtat().equals(orders.getState().getEtat())).findFirst();
        if(optionalState.isPresent()){
            int index = orderedStates.indexOf(optionalState.get());
            if(index<orderedStates.size()-1){
                orders.setState(orderedStates.get(index+1));
            }
        }
    }
}
