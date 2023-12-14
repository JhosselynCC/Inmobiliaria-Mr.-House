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
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        if (model != null) {
            model.addAttribute("roles", Rol.values());
            // Otros atributos y lógica necesaria para mostrar el formulario
            return "registration"; // suponiendo que "registration" es tu plantilla HTML
        } else {
            return "error";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario y contraseña invalidos");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ENTE','ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session) {
        Users loggedOn = (Users) session.getAttribute("usuariosession");

        if (loggedOn.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";

        }
        return "home.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ENTE','ROLE_ADMIN')")
    @GetMapping("/profile")
    public String profile(ModelMap modelo, HttpSession session) {
        Users user = (Users) session.getAttribute("usuariosession");
        modelo.put("user", user);

        return "user_modify.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ENTE','ROLE_ADMIN')")
    @PostMapping("/profile/{id}")
    public String update(@PathVariable Long id, @RequestParam String name, @RequestParam String email,
            @RequestParam String password, String password2, @RequestParam Long dni, @RequestParam Rol rol, Boolean active, ModelMap modelo) {

        try {
            userService.actualizar(id, name, email, password, password2, dni, rol, active);
            modelo.put("exito", "usuario fue actualizado correctamente");
            return ("/home.html");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);
            return "user_modify.html";
        }

    }

    //list
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listUsers")
    public String listUsers(ModelMap modelo) {
        List<Users> users = userService.listUsers();
        modelo.addAttribute("users", users);
        return "user_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/users/modify/{id}")
    public String userModify(ModelMap modelo, @PathVariable Long id) {
        Users users = userService.getOne(id);
        modelo.put("users", users);

        return "user_modify_admin.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users/modify/{id}")
    public String userModify(@PathVariable Long id, @RequestParam String name, @RequestParam String email,
            @RequestParam String password, String password2, @RequestParam Long dni, @RequestParam Rol rol, Boolean active, ModelMap modelo) {

        try {

            userService.actualizar(id, name, email, password, password2, dni, rol, active);
            modelo.put("exito", "usuario fue actualizado correctamente");
            return ("/panel.html");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);
            return "user_modify_admin.html";
        }

    }

}
