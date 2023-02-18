package com.barcode.barcode.model;

import com.barcode.barcode.enumeration.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String orderNumber;
    private String email;
    @ElementCollection(targetClass = StateEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "order_state", joinColumns = @JoinColumn(name = "order_id"))
    @Enumerated(EnumType.ORDINAL)
    private StateEnum State;
}
