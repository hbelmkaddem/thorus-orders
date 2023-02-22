package com.barcode.barcode.service.impl;

import com.barcode.barcode.Repository.StateRepository;
import com.barcode.barcode.model.Etats;
import com.barcode.barcode.service.StateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<Etats> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public Etats save(Etats etats) {
        return stateRepository.save(etats);
    }
}
