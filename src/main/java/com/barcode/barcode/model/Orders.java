package com.barcode.barcode.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date shippingDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    private String comment;
    private boolean notify;
    @OneToOne
    private Etats State;

    public String getEmailBody() {
        return "Nous vous informons que l'état de la commande : " + getOrderNumber() +" est passé à " + getState().getEtat();
    }
}
