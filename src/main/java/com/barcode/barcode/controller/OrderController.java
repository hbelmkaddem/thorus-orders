package com.barcode.barcode.controller;

import com.barcode.barcode.model.EmailDetails;
import com.barcode.barcode.model.Orders;
import com.barcode.barcode.service.EmailService;
import com.barcode.barcode.service.impl.QRCodeGenerator;
import com.barcode.barcode.service.impl.QRCodeServiceImpl;
import com.barcode.barcode.service.OrderService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/barcodes")
public class OrderController {
    private final QRCodeServiceImpl qrCodeService;
    private final OrderService orderService;
    private final EmailService emailService;
    @Value("${passcode}")
    private String pass;

    public OrderController(QRCodeServiceImpl qrCodeService, OrderService orderService, EmailService emailService) {
        this.qrCodeService = qrCodeService;
        this.orderService = orderService;
        this.emailService = emailService;
    }

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
    public byte[] getUserQRCode(@RequestBody Orders orders){
        String id = orderService.save(orders);
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
        Orders user= orderService.getUserName(id);
        return "Order Number : " + user.getOrderNumber() + " Email : " + user.getEmail() + " ! " ;
    }

    @GetMapping(value = "/pass/{passcode}/orders/{orderId}")
    public String getUserQRCode(@PathVariable String passcode, @PathVariable String orderId){
        if(!passcode.equals(this.pass)){
            return "Saisie le bon passcode!";
        }
        Orders orders = orderService.findById(orderId);
        orders.updateState();
        emailService.sendSimpleMail(new EmailDetails(orders.getEmail(),orders.getState(),"",""));
        return orderService.update(orders).getState();

    }
}
