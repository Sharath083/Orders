package com.task.orders.service.impl.mail;

import com.task.orders.config.ConfigParam;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.repository.OtpRepo;
import com.task.orders.service.impl.otp.OtpService;
import com.task.orders.service.impl.pdf.PdfGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.UUID;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.constants.InfoId.VALID;

@Service
public class EmailGenerator {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ConfigParam configParam;
    @Autowired
    SimpleMailMessage simpleMailMessage;
    @Autowired
    PdfGenerator pdfGenerator;
    @Autowired
    OtpRepo otpRepo;
    @Autowired
    OtpService otpService;

    public BaseResponse generateEmail(String mail, UUID userId,String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(mail);
            messageHelper.setSubject(ORDER_DETAILS);
            messageHelper.setText(Messages.ORDER_MAIL_SUBJECT);
            pdfGenerator.generatePdf(userId,userName);
            FileSystemResource file = new FileSystemResource(new File(ORDERS_PATHNAME));
            messageHelper.addAttachment(ORDERS_PDF, file);

            mailSender.send(message);
            return new BaseResponse(VALID, Messages.MAIL_SENT_SUCCESSFULLY + mail);
        } catch (MessagingException | FileNotFoundException | MalformedURLException e) {
            throw new CommonException(VALID, Messages.UNABLE_TO_SEND_MAIL_TO + mail, StatusCodes.SUCCESS);
        }
    }

    public BaseResponse sendOtp(String mail) {
        String otp = HelperFunctions.generateOtp();
        String msg = configParam.getMessage().replace("?", otp);
        otpService.storeOtp(mail,otp);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(OTP_SUBJECT);
        simpleMailMessage.setText(msg);

        System.out.println("--------------- "+mail);

        mailSender.send(simpleMailMessage);
//        redisHelper.set(OTP_REDIS_KEY + userId, otp, 5);

        return new BaseResponse(VALID, Messages.MAIL_SENT_SUCCESSFULLY + mail);
    }

//    public BaseResponse sendOtp(String mail, String userId) {
//        String otp = HelperFunctions.generateOtp();
//        String msg = configParam.getMessage().replace("?", otp);
//        simpleMailMessage.setTo(mail);
//        simpleMailMessage.setSubject(OTP_SUBJECT);
//        simpleMailMessage.setText(msg);
//
//        mailSender.send(simpleMailMessage);
//        redisHelper.set(OTP_REDIS_KEY + userId, otp, 5);
//
//        return new BaseResponse(VALID, Messages.MAIL_SENT_SUCCESSFULLY + mail);
//    }

}
