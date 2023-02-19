package com.barcode.barcode.Repository;

import com.barcode.barcode.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,String> {
}
