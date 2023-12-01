/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Services.PropertyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/comprar")
public class ComprarController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("")
    public String list(ModelMap modelo) {

        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);
        return "comprar.html";
    }
}
