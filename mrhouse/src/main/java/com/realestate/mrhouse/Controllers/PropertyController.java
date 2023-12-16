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
import com.realestate.mrhouse.Entities.ShiftsByProperty;
import com.realestate.mrhouse.Entities.Users;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.OffersByPropertyRepository;
import com.realestate.mrhouse.Repositories.PropertyRepository;
import com.realestate.mrhouse.Repositories.ShiftsByPropertyRepository;
import com.realestate.mrhouse.Repositories.UserRepository;
import com.realestate.mrhouse.Services.OffersByPropertyService;
import com.realestate.mrhouse.Services.PropertyService;
import com.realestate.mrhouse.Services.PublishersService;
import com.realestate.mrhouse.Services.ShiftsByPropertyService;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import static org.hibernate.internal.CoreLogging.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private OffersByPropertyRepository offersByPropertyRepository;

    @Autowired
    private ShiftsByPropertyRepository shiftsByPropertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    //CREAR PUBLICACIONES
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ENTE')")
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

            // Validar que el DNI ingresado existe en la lista de publishers
            Long dniPublisher = Long.parseLong(idPublisher);
            boolean publisherExists = publishersService.publisherExists(dniPublisher);
            if (!publisherExists) {
                // El DNI ingresado no existe en la lista de publishers
                throw new MyException("El DNI ingresado no coincide con ningún publicador existente");
            }

            propertyService.createProperty(images, typePublication, title, typeProperty, features, price, location, province, city, dniPublisher);
            modelo.put("exito", "la propiedad  fue cargado correctamente");

        } catch (NumberFormatException ex) {
            // Captura la excepción si el DNI ingresado no es un número válido
            modelo.put("error", "Ingrese un número válido como DNI");
            Logger.getLogger(PropertyController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MyException ex) {
            List<Publishers> publishers = publishersService.listPublishers();

            modelo.addAttribute("publishers", publishers);

            modelo.put("error", ex.getMessage());
            Logger.getLogger(PublishersController.class.getName()).log(Level.SEVERE, null, ex);

            return "property_create.html";
        }

        return "index.html";
    }

    //LISTAR TODAS LAS PUBLICACIONES 
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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

    //LISTAR POR RANKING 4 DE ULTMOS PUBLICADOS 
    @GetMapping("/ranking")
    public String mostrarUltimasPropiedades(ModelMap model) {
        List<Property> latestProperties = propertyService.getLatestAlquilerAndVentaProperties();
        model.addAttribute("properties", latestProperties);
        return "ranking.html";
    }

    //LISTAR PUBLICACIONES POR PUBLICADOR LOGUEADO
    @GetMapping("/propertyByPublisher")
    public String listPropertyByPublisher(Model model, Principal principal) {
        // Obtener el nombre de usuario del usuario logueado
        String username = principal.getName();
        // Obtener la entidad Users usando el nombre de usuario
        Users user = userRepository.searchByEmail(username);
        // Obtener el DNI del usuario
        Long dniPublisher = user.getDni();
        // Obtener las propiedades del publicador
        List<Property> properties = propertyRepository.findPropertiesByPublisherDni(dniPublisher);
        // Agregar las propiedades al modelo para mostrar en la vista
        model.addAttribute("properties", properties);
        return "propertyByPublisher_list.html";
    }

    /**
     * Controlamos las ofertas por propiedad
     *
     */
    //create
    @GetMapping("/offers/{id}")
    public String offers(@PathVariable Long id, ModelMap modelo) {
        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);

        return "offersByProperty_create.html";
    }

    @PostMapping("/offersRegistration/{id}")

    public String offersRegistration(@PathVariable Long id, @RequestParam String message,
            ModelMap modelo) {
        System.out.println(id + " " + message);
        try {
            offersByProperyService.createOffersByProperty(id, message);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listOffer")
    public String listOffer(ModelMap modelo) {
        List<OffersByProperty> offersByProperties = offersByProperyService.ListOffersByProperty();
        modelo.addAttribute("offersByProperties", offersByProperties);
        return "offersByProperty_list.html";
    }

    //modify
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

    //LISTAR OFERTAS HECHAS POR EL USUARIO LOGUEADO
    @GetMapping("/myOffers")
    public String OffersByUser(Model model, Principal principal) {
        try {
            // Obtener el correo electrónico del usuario logueado
            String userEmail = principal.getName();

            // Buscar ofertas por el correo electrónico del usuario
            List<OffersByProperty> offersByProperties = offersByPropertyRepository.findByUserEmail(userEmail);

            // Agregar las ofertas al modelo
            model.addAttribute("offersByProperties", offersByProperties);

            return "my_offers.html";

        } catch (Exception e) {
            // Manejar excepciones, por ejemplo, registrar el error y redirigir a una página de error
            model.addAttribute("error", "Error al recuperar las ofertas del usuario");
            return "error";
        }
    }

    //LISTAR OFERTAS RECIBIDAS POR PUBLICACIONES HECHAS POR PUBLICADOR LOGUEADO
    @GetMapping("/offersByPropertyPublisher")
    public String listOffersByProperty(Model model, Principal principal) {
        // Obtener el nombre de usuario del usuario logueado
        String username = principal.getName();
        // Obtener la entidad Users usando el nombre de usuario
        Users user = userRepository.searchByEmail(username);
        // Obtener el DNI del usuario
        Long dniPublisher = user.getDni();
        // Obtener las propiedades del publicador
        List<OffersByProperty> offersByProperties = offersByPropertyRepository.findByPublisherDni(dniPublisher);
        // Agregar las propiedades al modelo para mostrar en la vista
        model.addAttribute("offersByProperties", offersByProperties);

        return "offersByPropertyPublisher_list";
    }

    /**
     * *
     * Controlamos los turnos por propiedad
     *
     */
    //create
    @GetMapping("/shifts/{id}")
    public String shifts(@PathVariable Long id, ModelMap modelo) {
        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);
        return "shiftsByProperty_create.html";
    }

    @PostMapping("/shiftsRegistration/{id}")
    public String shiftsRegistration(@PathVariable Long id, @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String startTimeString,
            ModelMap modelo) throws ParseException {
        // Parsear la cadena a un objeto Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date startTime;

        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            startTime = dateFormat.parse(startTimeString);
            shiftsByPropertyService.createShiftsByProperty(id, startTime);
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

    //LISTA TODOS LOS TURNOS
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listShift")
    public String listShifts(ModelMap modelo) {
        List<ShiftsByProperty> shiftsByProperties = shiftsByPropertyService.ListShiftsByProperty();
        modelo.addAttribute("shiftsByProperties", shiftsByProperties);
        return "shiftsByProperty_list.html";
    }

    @GetMapping("/modifyShift/{Id}")

    public String modifyShift(@PathVariable Long Id, ModelMap modelo) {
        modelo.put("ShiftsByProperty", shiftsByPropertyService.getOne(Id));
        List<Property> properties = propertyService.listProperties();
        modelo.addAttribute("properties", properties);
        return "shiftsByProperty_modify.html";
    }

    @PostMapping("/modifyShift/{Id}")
    public String modifyShift(@PathVariable Long Id, Long IdProperty, String userEmail, String shiftStatus, ModelMap modelo) {

         
        try {

            // Aquí estás intentando analizar la cadena startTime
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            //Date parsedDate = dateFormat.parse(startTime.toString());
            shiftsByPropertyService.modifyShiftsByProperty(Id, IdProperty, userEmail, shiftStatus);

            return  "home.html";//"home.html"; //"shiftsByPropertyPublisher_list.html";//"redirect:../listShift";
        } catch (MyException ex) {
            List<Property> properties = propertyService.listProperties();
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("properties", properties);
            return "shiftsByProperty_modify.html";

        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.put("error", "Error al analizar la fecha");
            return "shiftsByProperty_modify.html";
        }

    }

    //LISTAR TURNOS ENVIADOS POR EL USUARIO
    @GetMapping("/myShifts")
    public String ShiftsByUser(Model model, Principal principal) {
        try {
            // Obtener el correo electrónico del usuario logueado
            String userEmail = principal.getName();

            // Buscar ofertas por el correo electrónico del usuario
            List<ShiftsByProperty> shiftsByProperties = shiftsByPropertyRepository.findByUserEmail(userEmail);

            // Agregar las ofertas al modelo
            model.addAttribute("shiftsByProperties", shiftsByProperties);

            return "my_shifts.html";

        } catch (Exception e) {
            // Manejar excepciones, por ejemplo, registrar el error y redirigir a una página de error
            model.addAttribute("error", "Error al recuperar los turnos del usuario");
            return "error";
        }
    }

    //LISTAR TURNOS RECIBIDOS POR PUBLICACIONES HECHAS POR PUBLICADOR LOGUEADO
    @GetMapping("/shiftsByPropertyPublisher")
    public String listShiftsByProperty(Model model, Principal principal) {
        // Obtener el nombre de usuario del usuario logueado
        String username = principal.getName();
        // Obtener la entidad Users usando el nombre de usuario
        Users user = userRepository.searchByEmail(username);
        // Obtener el DNI del usuario
        Long dniPublisher = user.getDni();
        // Obtener las propiedades del publicador
        List<ShiftsByProperty> shiftsByProperties = shiftsByPropertyRepository.findByPublisherDni(dniPublisher);
        // Agregar las propiedades al modelo para mostrar en la vista
        model.addAttribute("shiftsByProperties", shiftsByProperties);

        return "shiftsByPropertyPublisher_list.html";
    }
    
    @GetMapping("/editProperty/{id}")
    public String editProperty(@PathVariable Long id, ModelMap modelo) {
      
        modelo.put("property", propertyService.getOne(id));
        
        List<Publishers> publishers = publishersService.listPublishers();
        
        modelo.addAttribute("publishers", publishers);
        
        return "property_modify.html";
    }

    @PostMapping("/editProperty/{id}")
    public String editProperty(@PathVariable Long id, String typePublication, String title, String typeProperty, String features, Double price, String location, String province, String city, Long idPublishers, ModelMap modelo) {
        try {
            List<Publishers> publishers = publishersService.listPublishers();
            
            modelo.addAttribute("publishers", publishers);

            propertyService.editProperty(id, typePublication, title, typeProperty, features, price, location, province, city, idPublishers);
            
                        
            return "index.html";

        } catch (MyException ex) {
            List<Publishers> publishers = publishersService.listPublishers();
            
            modelo.put("error", ex.getMessage());
            
            modelo.addAttribute("publishers", publishers);
            
            return "property_modify.html";
        }

    }
    
    

}
