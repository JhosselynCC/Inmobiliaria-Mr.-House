/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

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

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/publisher")
public class PublishersController {
    
    @Autowired
    private PublishersService publishersService;

    @GetMapping("/register")
    public String register() {
        return "publisher_create.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String name, ModelMap modelo) {

        try {
            publishersService.createPublishersService(name);
            modelo.put("exito", "el publicador  fue cargado correctamente");

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PublishersController.class.getName()).log(Level.SEVERE, null, ex);

            return "publisher_create.html";
        }
        // System.out.println("nombre" +nombre);
        return "index.html";
    }
/*
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<autor> autores = autorServcio.ListarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorServcio.getOne(id));
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            autorServcio.ModificarAutor(nombre, id);
            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }

    }
    */
}
