/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.smtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2171584201008
 */
@Service
public class EmailServices {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String destinatario,String asunto,String mensaje) {
        SimpleMailMessage correo=new SimpleMailMessage();
        correo.setFrom("nec.adx1@gmail.com");
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(mensaje);
        
        emailSender.send(correo);
        System.out.println("mensaje enviado correctamente");
//usar mailSender acá...


    }

}
    

