package com.barcode.barcode.service;

import com.barcode.barcode.model.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}