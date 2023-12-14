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
import com.realestate.mrhouse.Enums.City;
import com.realestate.mrhouse.Enums.TypeProperty;
import com.realestate.mrhouse.Services.PropertyService;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<Property> properties = propertyService.listAlquiler();

        modelo.addAttribute("properties", properties);

        return "alquilar.html";
    }

    @GetMapping("/property/{id}")
    public String alquilarProperty(@PathVariable Long id, ModelMap modelo) {

        modelo.put("property", propertyService.getOne(id));

        List<Property> properties = propertyService.listAlquiler3(id);

        modelo.addAttribute("properties", properties);

        return "detail_property.html";
    }

  
    @GetMapping("/search")
    public String searchProperties(
            @RequestParam(name = "city", required = false) City city,
            @RequestParam(name = "type", required = false) TypeProperty type,
           @RequestParam(name = "price", required = false) Double price,
            ModelMap model) {

        // Verifica si se han proporcionado parámetros de filtro
        if (city != null && city != City.TODOS || type != null && type != TypeProperty.TODOS || price != null ) {
            // Aplica filtros solo si al menos uno de los parámetros no es nulo
            List<Property> properties = propertyService.findPropertiesByCityAndType(city,type,price);
                 
            model.addAttribute("properties", properties);
        } else {
            // Si ambos parámetros son nulos, obtén todas las propiedades sin filtros
            List<Property> allProperties = propertyService.listAlquiler();
            model.addAttribute("properties", allProperties);
        }

        return "alquilar.html";
    }
    
    

}
