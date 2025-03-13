package com.management.kafka_mailSender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KafkaConsumerService {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "user_topic", groupId = "employee_group")
    public void consumerMessage(String email){
        System.out.println("received email " + email);


        if (email != null) {
            sendMail(email, "Welcome!", "You have been added to the group!");
        } else {
            System.out.println("Invalid email format  " + email);
        }

    }
//    private String extractEmail(String message){
////        return message.substring(message.indexOf("Email: ") + 7).trim();
//        Pattern pattern = Pattern.compile("Email:\\s*([^)]+)");
//        Matcher matcher = pattern.matcher(message);
//        if (matcher.find()) {
//            return matcher.group(1).trim();
//        }
//        return null;
//    }

    private void sendMail(String to, String subject,String body) {

        try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        mailSender.send(message);
        System.out.println("Email sent to: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
