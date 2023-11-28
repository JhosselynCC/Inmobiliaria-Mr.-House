/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Services.PropertyService;
import com.realestate.mrhouse.Services.PublishersService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PublishersService publishersService;

    @GetMapping("/register")
    public String register(ModelMap modelo) {

        List<Publishers> publishers = publishersService.listPublishers();

        modelo.addAttribute("publishers", publishers);

        return "property_create.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String typePublication, @RequestParam String title, @RequestParam String typeProperty,
            @RequestParam String features, @RequestParam(required = false) Double price,
            String location, String province, String city, @RequestParam String idPublisher,
            ModelMap modelo, MultipartFile images) {

        try {
            propertyService.createProperty(images, typePublication, title, typeProperty, features, price, location, province, city, idPublisher);
            modelo.put("exito", "la propiedad  fue cargado correctamente");

        } catch (MyException ex) {
            List<Publishers> publishers = publishersService.listPublishers();

            modelo.addAttribute("publishers", publishers);

            modelo.put("error", ex.getMessage());
            Logger.getLogger(PublishersController.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println();

            return "property_create.html";
        }

        return "index.html";
    }

    @GetMapping("/list")
    public String list(ModelMap modelo) {

        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);
        return "property_list.html";
    }
    
 
}
