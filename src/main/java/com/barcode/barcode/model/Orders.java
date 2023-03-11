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
    private Date arrivalDate;
    private Date shippingDate;
    private Date updatedAt;

    private String comment;
    private boolean notify;
    @OneToOne
    private Etats State;

    public String getEmailBody() {
        return "Bonjour !\n" +
                "\n" +
                "Votre commande N° "+ getOrderNumber() +" passe en " + getState().getEtat() +
                "\n" +
                "L’équipe Thorus \n" +
                "\n" +
                "Catalogue : https://thorus-wear.com/catalogue/\n" +
                "\n" +
                "Boutique : https://thorus-wear.com/fr/\n" +
                "\n" +
                "Insta : https://www.instagram.com/thorus_wear/";
    }
}
