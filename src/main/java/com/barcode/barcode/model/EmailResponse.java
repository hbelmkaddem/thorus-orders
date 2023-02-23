package com.barcode.barcode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailResponse {
    private String state;
    private String email;
    private boolean notify;
    private String orderNumber;
}
