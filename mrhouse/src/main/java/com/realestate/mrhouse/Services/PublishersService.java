/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Entities.Users;
import com.realestate.mrhouse.Enums.TypeOwner;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Relations.Rol;
import com.realestate.mrhouse.Repositories.PublishersRepository;
import com.realestate.mrhouse.Repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2171584201008
 */
@Service
public class PublishersService {

    @Autowired
    PublishersRepository publishersRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createPublishersService(Long dni, String name, String typeOwner) throws MyException {

        validar(name);

        // Obtener la información del usuario logueado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Obtener el email del usuario logueado
        String loggedUserEmail = userDetails.getUsername();
        // Buscar al usuario por email en el repositorio
        Users loggedUser = userRepository.searchByEmail(loggedUserEmail);

        if (loggedUser != null) {
            // Verificar que el usuario logueado tenga el rol "ente"
            if (loggedUser.getRol() == Rol.ENTE) {
                // Comparar el DNI del usuario logueado con los datos proporcionados
                Long loggedUserDni = loggedUser.getDni();

                if (loggedUserDni != null && loggedUserDni.equals(dni)) {
                    // El DNI coincide con el usuario logueado, proceder con la lógica de creación
                    Publishers publishers = new Publishers();
                    publishers.setDni(loggedUserDni);

                    publishers.setName(name);
                    publishers.setTypeOwner(TypeOwner.valueOf(typeOwner));
                    publishersRepository.save(publishers);
                } else {
                    // El DNI no coincide con el usuario logueado, lanzar excepción o manejar según tus requerimientos
                    throw new MyException("El DNI proporcionado no coincide con el del usuario logueado");
                }
            } else {
                // El usuario logueado no tiene el rol "ente", lanzar excepción o manejar según tus requerimientos
                throw new MyException("El usuario logueado no tiene el rol 'ente'");
            }
        } else {
            // El usuario logueado no fue encontrado, lanzar excepción o manejar según tus requerimientos
            throw new MyException("No se encontró al usuario logueado");
        }
    }

    /*
     @Transactional
public void createPublishersService(String name, String typeOwner) throws MyException {
    validar(name);

    // Obtener la información del usuario logueado
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    // Obtener el email del usuario logueado
    String loggedUserEmail = userDetails.getUsername();

    // Buscar al usuario por email en el repositorio
    Users loggedUser = userRepository.searchByEmail(loggedUserEmail);

    if (loggedUser != null) {
        // Verificar que el usuario logueado tenga el rol "ente"
        if (loggedUser.getRol() == Rol.ENTE) {
            // Comparar el DNI del usuario logueado con los datos proporcionados
            Long loggedUserDni = loggedUser.getDni();

            if (loggedUserDni != null && loggedUserDni.equals(dni)) {
                // El DNI coincide con el usuario logueado, proceder con la lógica de creación
                Publishers publishers = new Publishers();
                publishers.setName(name);
                publishers.setTypeOwner(TypeOwner.valueOf(typeOwner));
                publishersRepository.save(publishers);
            } else {
                // El DNI no coincide con el usuario logueado, lanzar excepción o manejar según tus requerimientos
                throw new MyException("El DNI proporcionado no coincide con el del usuario logueado");
            }
        } else {
            // El usuario logueado no tiene el rol "ente", lanzar excepción o manejar según tus requerimientos
            throw new MyException("El usuario logueado no tiene el rol 'ente'");
        }
    } else {
        // El usuario logueado no fue encontrado, lanzar excepción o manejar según tus requerimientos
        throw new MyException("No se encontró al usuario logueado");
    }
}   
        
     
   @Transactional
    public void createPublishersService(String name, String typeOwner) throws MyException {
        validar(name);

        // Obtener el DNI del usuario logueado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserDni = ((CustomUserDetails) authentication.getPrincipal()).getDni();

        // Comparar el DNI del usuario logueado con el DNI proporcionado
        if (loggedUserDni != null && loggedUserDni.equals(dni)) {
            // El DNI coincide con el usuario logueado, proceder con la lógica de creación
            Publishers publishers = new Publishers();
            publishers.setName(name);
            publishers.setTypeOwner(TypeOwner.valueOf(typeOwner));
            publishersRepository.save(publishers);
        } else {
            // El DNI no coincide con el usuario logueado, lanzar excepción o manejar según tus requerimientos
            throw new MyException("El DNI proporcionado no coincide con el del usuario logueado");
        }
    }

        
        
         @Transactional
    public void createPublishersService(Long dni, String name, String typeOwner) throws MyException {
        validar(name);

        // Buscar usuario por DNI y rol "ente"
        Optional<Users> userOptional = usersRepository.findByDniAndRol(dni, Rol.ENTE);

        if (userOptional.isPresent()) {
            // El usuario con el DNI y rol "ente" existe
            Publishers publishers = new Publishers();
            publishers.setName(name);
            publishers.setTypeOwner(TypeOwner.valueOf(typeOwner));
            publishersRepository.save(publishers);
        } else {
            // El usuario no fue encontrado, lanzar excepción o manejar de acuerdo a tus requerimientos
            throw new MyException("No se encontró un usuario con el DNI proporcionado y rol 'ente'");
        }
    }

     */
    public List<Publishers> listPublishers() {
        List<Publishers> publishers = new ArrayList();
        publishers = publishersRepository.findAll();
        return publishers;
    }

    public void EditPublishers(String name, Long id) throws MyException {
        validar(name);

        Optional<Publishers> respuesta = publishersRepository.findById(id);

        if (respuesta.isPresent()) {
            Publishers a = respuesta.get();

            a.setName(name);

            publishersRepository.save(a);

        }

    }

    public Publishers getOne(Long id) {
        return publishersRepository.getOne(id);
    }

    private void validar(String name) throws MyException {

        if (name == null) {
            throw new MyException("El nombre o razon social  no puede estar vacio");
        }

    }

    private void validarDNI(Long dni) throws MyException {
        if (dni == null) {
            throw new MyException("El dni  no puede estar vacio");
        }

    }

    public boolean publisherExists(Long dni) {
        Optional<Publishers> publisherOptional = publishersRepository.findById(dni);
        return publisherOptional.isPresent();
    }
}
