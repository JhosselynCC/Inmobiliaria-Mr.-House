/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author guido
 */
@Controller
@RequestMapping("/contacto")
public class ContactoController {
   
    @GetMapping("")
    public String contacto() {

        return "contacto.html";
    }
}
