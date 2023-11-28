/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Services.PublishersService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/publishers")
public class PublishersController {

    @Autowired
    private PublishersService publishersService;

    @GetMapping("/register")
    public String register() {
        return "publishers_create.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String name, @RequestParam String typeOwner, ModelMap modelo) {

        try {
            publishersService.createPublishersService(name, typeOwner);
            modelo.put("exito", "el publicador  fue cargado correctamente");

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PublishersController.class.getName()).log(Level.SEVERE, null, ex);

            return "publishers_create.html";
        }
        // System.out.println("nombre" +nombre);
        return "index.html";
    }

    @GetMapping("/list")
    public String list(ModelMap modelo) {

        List<Publishers> publishers = publishersService.listPublishers();
        modelo.addAttribute("publishers", publishers);
        return "publishers_list.html";
    }

}
