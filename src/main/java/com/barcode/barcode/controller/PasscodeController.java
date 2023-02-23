package com.barcode.barcode.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/passcode")
public class PasscodeController {
    @Value("${passcode}")
    private String pass;
    @GetMapping("/{passcode}")
    public boolean getPasscode(@PathVariable String passcode){
        return pass.equals(passcode);
    }
}
