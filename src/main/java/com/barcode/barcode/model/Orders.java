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
public class Orders {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String orderNumber;
    private String email;
    private String State;

    public void updateState() {
        this.setState(switch (getState()){
            case "Reception"-> StateEnum.Validation.name();
            case "Validation"->StateEnum.préparation.name();
            case "préparation","Acheminement"->StateEnum.Acheminement.name();
            default -> "";
        });
    }
}
