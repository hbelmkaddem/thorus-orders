package com.barcode.barcode.service;

import com.barcode.barcode.model.Etats;

import java.util.List;

public interface StateService {
    List<Etats> findAll();

    Etats save(Etats etats);
}
