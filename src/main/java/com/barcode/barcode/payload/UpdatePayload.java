package com.barcode.barcode.payload;

import com.barcode.barcode.model.Etats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePayload {
    private Etats etats;
    private String id;
}
