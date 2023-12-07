/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Contacto;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Services.ContactoService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author guido
 */
@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping("")
    public String registrar() {

        return "contacto.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String nombre, @RequestParam String email, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String typeContacto, @RequestParam String mensaje, ModelMap modelo) {
        try {
            contactoService.createContacto(typeContacto, nombre, email, telefono, direccion, mensaje);
            modelo.put("exito", "El mensaje fue enviado correctamente, pronto tendra su respuesta");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(ContactoController.class.getName()).log(Level.SEVERE, null, ex);
            return "Contacto.html";
        }
        return "contacto.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Contacto> contactos = contactoService.listContacts();
        modelo.addAttribute("contactos", contactos);
        return "contactolist.html";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, ModelMap modelo) {
        modelo.put("contacto", contactoService.getOne(id));
        
        return "vermensaje.html";
    }

    /*
    
    @GetMapping("/lista")
    public String listar (ModelMap modelo){
        List <Contacto> contactos = contactoService.listContacts();
        
        modelo.addAttribute("contactos", contactos);
        
        return "contactolist.html";
    }
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, ModelMap modelo){
        modelo.put("contacto", contactoService.getOne(id));
        List <Contacto> contacto = contactoService.listContacts();
        modelo.addAttribute("contacto", contacto);
        return "vermensaje.html";
    }
    
//    @PostMapping("/ver/{id}")
//    public String mostrar(@PathVariable Long id, ModelMap modelo){
//        try {
//             Contacto contacto = contactoService.getOne(id);
//             return "redirect:../lista";
//        } catch (Exception e) {
//            return "contactolist.html";
//        }
       
    
    
    
    
    @GetMapping("/modificar/{ISBN}")

    public String modificar(@PathVariable Long ISBN, ModelMap modelo) {
        modelo.put("Libro", LibroServcio.getOne(ISBN));

        List<autor> autores = autorServcio.ListarAutores();
        List<editorial> editoriales = editorialServcio.ListarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";

    }

    @PostMapping("/modificar/{ISBN}")
    public String modificar(@PathVariable Long ISBN, String titulo, Integer ejemplares, String IdAutor, String IdEditorial, ModelMap modelo) {

        try {

           // List<autor> autores = autorServcio.ListarAutores();
            //List<editorial> editoriales = editorialServcio.ListarEditoriales();

           // modelo.addAttribute("autores", autores);
            //modelo.addAttribute("editoriales", editoriales);

            LibroServcio.ModificarLibro(ISBN, titulo, ejemplares, IdAutor, IdEditorial);
            return "redirect:../listar";
            
            
        } catch (MiException ex) {

            List<autor> autores = autorServcio.ListarAutores();
            List<editorial> editoriales = editorialServcio.ListarEditoriales();
            
            modelo.put("error", ex.getMessage());
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_modificar.html";
        }
    }
     */
}
