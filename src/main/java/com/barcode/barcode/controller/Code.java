package com.barcode.barcode.controller;

import com.barcode.barcode.model.Order;
import com.barcode.barcode.service.impl.QRCodeGenerator;
import com.barcode.barcode.service.impl.QRCodeServiceImpl;
import com.barcode.barcode.service.UserService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/barcodes")
public class Code {
    @Autowired
    private QRCodeServiceImpl qrCodeService;
    @Autowired
    private UserService userService;

    @Value("${passcode}")
    private String pass;

    @PostMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getQRCode(@RequestBody String medium){
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(medium,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    @PostMapping(value = "/read",consumes = {"multipart/form-data" })
    public String read(@RequestBody() MultipartFile file) throws IOException {
        return qrCodeService.readQRCode(file.getBytes());
    }

    @PostMapping(value = "/order", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserQRCode(@RequestBody Order order){
        String id = userService.save(order);
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(id,150,150);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @PostMapping(value = "/order/read",consumes = {"multipart/form-data" })
    public String readUser(@RequestBody() MultipartFile file) throws IOException {
        String id = qrCodeService.readQRCode(file.getBytes());
        Order user=userService.getUserName(id);
        return "Hello " + user.getOrderNumber() + " " + user.getEmail() + " ! " ;
    }

    @GetMapping(value = "/pass/{passcode}/orders/{orderId}")
    public String getUserQRCode(@PathVariable String passcode, @PathVariable String orderId){
        if(passcode.equals(this.pass)){
            return "Saisie le bon passcode!";
        }
        Order order = userService.findById(orderId)
    }
}
