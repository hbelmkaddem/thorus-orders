package com.barcode.barcode.controller;

import com.barcode.barcode.model.EmailDetails;
import com.barcode.barcode.model.EmailResponse;
import com.barcode.barcode.model.Orders;
import com.barcode.barcode.model.Results;
import com.barcode.barcode.payload.UpdatePayload;
import com.barcode.barcode.service.EmailService;
import com.barcode.barcode.service.OrderService;
import com.barcode.barcode.service.impl.QRCodeGenerator;
import com.barcode.barcode.service.impl.QRCodeServiceImpl;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderController {
    private final QRCodeServiceImpl qrCodeService;
    private final OrderService orderService;
    private final EmailService emailService;
    @Value("${passcode}")
    private String pass;
    @Value("${url}")
    private String url;

    public OrderController(QRCodeServiceImpl qrCodeService, OrderService orderService, EmailService emailService) {
        this.qrCodeService = qrCodeService;
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/save")
    public Results getResultQRCode(@RequestBody Orders orders){
        String id = orderService.save(orders);
        Results result=new Results();
        byte[] image = new byte[0];
        try {
            image = QRCodeGenerator.getQRCodeImage(url+id,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        result.setOrder(orders);
        result.setImage(image);
        return result;
    }

    @PostMapping(value = "/read",consumes = {"multipart/form-data" })
    public String readOrder(@RequestBody() MultipartFile file) throws IOException {
        String id = qrCodeService.readQRCode(file.getBytes());
        Orders order= orderService.getUserName(id);
        return "Order Number : " + order.getOrderNumber() + " Email : " + order.getEmail() + " ! " ;
    }

    @PostMapping(value = "/update")
    public EmailResponse getStatus(@RequestBody UpdatePayload payload){
        if(!payload.getPasscode().equals(this.pass)){
            return new EmailResponse();
        }
        Orders orders = orderService.findById(payload.getId());
        ///orderService.updateState(orders);
        orders.setState(payload.getEtats());
        orders.setUpdatedAt(new Date());
        emailService.sendSimpleMail(new EmailDetails(orders.getEmail(),orders.getEmailBody(),"État de votre commande : "+orders.getOrderNumber(),""));
        Orders updated = orderService.update(orders);
        return new EmailResponse(updated.getState().getEtat(),updated.getEmail());
    }
    @GetMapping("/allOrders")
    public List<Orders> getAllLists(){
        return orderService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        orderService.delete(id);
    }
}
