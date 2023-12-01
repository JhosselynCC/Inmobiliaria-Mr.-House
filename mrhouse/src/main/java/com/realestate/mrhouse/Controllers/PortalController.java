/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Users;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Relations.Rol;
import com.realestate.mrhouse.Services.UserService;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/register")
    public String register() {
        return "registration.html";
    }

    @PostMapping("/registration")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password, String password2, @RequestParam Long dni, @RequestParam Rol rol,
            ModelMap modelo) {

        try {
            userService.register(name, email, password, password2, dni, rol);
            modelo.put("exito", "usuario fue cargado correctamente");
            return "index.html";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("rol", rol);

            return "registration.html";
        }
    }

    @GetMapping("/registration")
    public String mostrarFormularioRegistro(ModelMap model) {
        model.addAttribute("roles", Rol.values());
        // Otros atributos y lógica necesaria para mostrar el formulario
        return "registration"; // suponiendo que "registration" es tu plantilla HTML
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario y contraseña invalidos");

        }
        return "login.html";
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/home")
    public String home (HttpSession session) {
        Users loggedOn = (Users) session.getAttribute("usuariosession");
        if (loggedOn.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";

        }
        return "home.html";
    }
}
