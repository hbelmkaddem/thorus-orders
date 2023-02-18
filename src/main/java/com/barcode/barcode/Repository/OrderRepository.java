package com.barcode.barcode.Repository;

import com.barcode.barcode.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
