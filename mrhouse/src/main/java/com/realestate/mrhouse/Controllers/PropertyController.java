/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Image;
import com.realestate.mrhouse.Entities.OffersByProperty;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Services.OffersByPropertyService;
import com.realestate.mrhouse.Services.PropertyService;
import com.realestate.mrhouse.Services.PublishersService;
import com.realestate.mrhouse.Services.ShiftsByPropertyService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private OffersByPropertyService offersByProperyService;

    @Autowired
    private ShiftsByPropertyService shiftsByPropertyService;

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
            ModelMap modelo, List<MultipartFile> images) {

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

        for (Property property : properties) {
            System.out.println("Property ID: " + property.getId());
            if (property.getImage() != null) {
                for (Image image : property.getImage()) {
                    System.out.println("Image ID: " + image.getId());
                }
            }
        }
        return "property_list.html";
    }

    @GetMapping("/list/{propertyId}")
    public String viewProperty(@PathVariable String propertyId, ModelMap modelo) {
        List<Image> imagesProperty = propertyService.obtainImageByProperty(propertyId);
        modelo.addAttribute("imagesProperty", imagesProperty);
        return "detail_property.html";
    }

    /**
     * Controlamos las ofertas por propiedad
     */
    //create
    @GetMapping("/offers")

    public String offers(ModelMap modelo) {

        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);

        return "offersByProperty_create.html";
    }

    @PostMapping("/offersRegistration/{id}")

    public String offersRegistration(@PathVariable Long IdProperty, @RequestParam String message,
            ModelMap modelo) {

        try {
            offersByProperyService.createOffersByProperty(IdProperty, message);
            modelo.put("exito", "la oferta  fue cargado correctamente");
        } catch (MyException ex) {
            List<Property> properties = propertyService.listProperties();
            modelo.addAttribute("properties", properties);

            modelo.put("error", ex.getMessage());
            Logger.getLogger(PropertyController.class.getName()).log(Level.SEVERE, null, ex);
            return "offersByProperty_create.html";
        }

        return "index.html";
    }

    //list
    @GetMapping("/listOffer")
    public String listOffer(ModelMap modelo) {
        List<OffersByProperty> offersByProperties = offersByProperyService.ListOffersByProperty();
        modelo.addAttribute("offersByProperties", offersByProperties);
        return "offersByProperty_list.html";
    }

    @GetMapping("/modifyOffer/{Id}")

    public String modifyOffer(@PathVariable Long Id, ModelMap modelo) {
        modelo.put("OffersByProperty", offersByProperyService.getOne(Id));

        List<Property> properties = propertyService.listProperties();

        modelo.addAttribute("properties", properties);

        return "offersByProperty_modify.html";

    }

    @PostMapping("/modifyOffer/{Id}")

    public String modifyOffer(@PathVariable Long Id, Long IdProperty, String message, String userEmail, String statusOffer, ModelMap modelo) {
        try {
            offersByProperyService.modifyOffersByProperty(Id, IdProperty, message, userEmail, statusOffer);
            return "redirect:../listOffer";
        } catch (MyException ex) {
            List<Property> properties = propertyService.listProperties();
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("properties", properties);
            return "offersByProperty_modify.html";

        }

    }

    /**
     * Controlamos los turnos por propiedad
     */
    //create
    @GetMapping("/shifts")

    public String shifts(ModelMap modelo) {

        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);

        return "shiftsByProperty_create.html";
    }

    @PostMapping("/shiftsRegistration")

    public String shiftsRegistration(@RequestParam Long IdProperty, @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String startTimeString,
            ModelMap modelo) throws ParseException {

        // Parsear la cadena a un objeto Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date startTime;

        try {
            startTime = dateFormat.parse(startTimeString);
            shiftsByPropertyService.createShiftsByProperty(IdProperty, startTime);
            modelo.put("exito", "El turno fue cargado correctamente");

        } catch (MyException ex) {
            List<Property> properties = propertyService.listProperties();
            modelo.addAttribute("properties", properties);

            modelo.put("error", ex.getMessage());
            
            Logger.getLogger(PropertyController.class.getName()).log(Level.SEVERE, null, ex);
            return "shiftsByProperty_create.html";
        }

        return "index.html";
    }

}
