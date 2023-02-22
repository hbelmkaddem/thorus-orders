package com.barcode.barcode.controller;

import com.barcode.barcode.model.Etats;
import com.barcode.barcode.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/states")
public class StateController {
    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/")
    public List<Etats> getAll(){
        return stateService.findAll();
    }
    @PostMapping("/add")
    public Etats save(@RequestBody Etats etats){
        return stateService.save(etats);
    }
}
