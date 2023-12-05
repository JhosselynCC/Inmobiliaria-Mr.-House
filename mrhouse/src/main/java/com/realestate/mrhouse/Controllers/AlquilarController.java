/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Services.PropertyService;

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/alquilar")
public class AlquilarController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("")
    public String alquilar(ModelMap modelo) {

        List<Property>properties = propertyService.listAlquiler();

        modelo.addAttribute("properties",properties);

        return "alquilar.html";
    }




    @GetMapping("/property/{id}")
    public String alquilarProperty(@PathVariable Long id, ModelMap modelo) {

        modelo.put("property", propertyService.getOne(id));

        List<Property>properties = propertyService.listAlquiler3(id);

        modelo.addAttribute("properties",properties);

        return "detail_property.html";
    }

/*
    @GetMapping("/contacto")
    public String mostrarPaginaContacto() {
    return "contacto.html"; // Este sería el nombre de tu plantilla Thymeleaf para la página de contact
    }
*/

}
