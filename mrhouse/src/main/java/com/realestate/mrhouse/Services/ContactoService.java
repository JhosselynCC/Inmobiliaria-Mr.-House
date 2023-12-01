/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Contacto;
import com.realestate.mrhouse.Enums.TypeContacto;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.ContactoRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ContactoService {
    @Autowired
    private ContactoRepository contactoRepository;
    
    @Transactional
    public void createContacto (String typeContacto, String nombre, String email, String telefono, String direccion, String mensaje) throws MyException {
        validar (typeContacto, nombre, email, telefono, direccion, mensaje);
        
        Contacto contacto = new Contacto();
        
        contacto.setTypeContacto(TypeContacto.valueOf(typeContacto));
        contacto.setNombre(nombre);
        contacto.setEmail(email);
        contacto.setTelefono(telefono);
        contacto.setDireccion(direccion);
        contacto.setMensaje(mensaje);
        contacto.setAlta(new Date());
        contactoRepository.save(contacto);
    }
    
    public List<Contacto> listContacts(){
        List<Contacto> contactos = new ArrayList();
        
        contactos = contactoRepository.findAll();
        
        return contactos;
    }
    
    public Contacto getOne(Long id){
        return contactoRepository.getOne(id);
    }
    
    
    private void validar(String typeContacto, String nombre, String email, String telefono, String direccion, String mensaje) throws MyException {

        if (typeContacto == null) {
            throw new MyException("El tipo de propiedad no puede ser nulo");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("Las caracteristicas no pueden ser nulas o estar vacias");
        }
        
        if (email.isEmpty() || email == null) {
            throw new MyException("El email no puede ser nulo o estar vacio");
        }
        if (telefono.isEmpty() || telefono== null) {
            throw new MyException("El telefono no puede ser nulo o estar vacio");
        }
        if (direccion.isEmpty() || direccion== null ) {
            throw new MyException("La direccion no puede ser nula o estar vacia");
        }
        if (mensaje.isEmpty() || mensaje == null) {
            throw new MyException("El mensaje no puede ser nulo o estar vacio");
        }

    }
}
