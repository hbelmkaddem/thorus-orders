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
    public EmailResponse getStatus(@RequestBody Orders payload){
        Orders orders = orderService.findById(payload.getId());
        orders.setState(payload.getState());
        if(!payload.getComment().isEmpty())
            orders.setComment(payload.getComment());
        orders.setUpdatedAt(new Date());
        Orders updated = orderService.update(orders);
        if(orders.isNotify())
            emailService.sendSimpleMail(new EmailDetails(updated.getEmail(),updated.getEmailBody(),"État de la commande : "+updated.getOrderNumber(),""));
        return new EmailResponse(updated.getState().getEtat(),updated.getEmail(),updated.isNotify(),updated.getOrderNumber());
    }
    @GetMapping("/allOrders")
    public List<Orders> getAllLists(){
        return orderService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        orderService.delete(id);
    }

    @GetMapping(value = "/get-QrCode/{orderId}")
    public Results getResultQRCode(@PathVariable String orderId ){
        Results result=new Results();
        Orders orders = orderService.findById(orderId);
        byte[] image = new byte[0];
        try {
            image = QRCodeGenerator.getQRCodeImage(url+orderId,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        result.setOrder(orders);
        result.setImage(image);
        return result;
    }
    @GetMapping("/{id}")
    public Orders findById(@PathVariable String id){
        return orderService.findById(id);
    }
}
