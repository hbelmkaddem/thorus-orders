package com.barcode.barcode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String fullname;
    private String email;
    private String orderNumber;

    private Date arrivalDate;
    private Date shippingDate;
    private Date updatedAt;
    @OneToOne
    private Etats State;

    public String getEmailBody() {
        return "Nous vous informons que l'état de votre commande : " + getOrderNumber() +" devient " + getState().getEtat();
    }
}
