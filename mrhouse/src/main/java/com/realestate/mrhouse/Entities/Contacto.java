/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Entities;

import com.realestate.mrhouse.Enums.TypeContacto;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Usuario
 */
@Entity
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @Enumerated(EnumType.STRING)
    private TypeContacto typeContacto;
    
    String nombre;
    String email;
    String telefono;
    String direccion;
    String mensaje;
    
 @Temporal(TemporalType.DATE)
    private Date alta;
    
    public Contacto() {
    }

    public Contacto(Long Id, TypeContacto typeContacto, String nombre, String email, String telefono, String direccion, String mensaje, Date alta) {
        this.Id = Id;
        this.typeContacto = typeContacto;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.mensaje = mensaje;
        this.alta = alta;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public TypeContacto getTypeContacto() {
        return typeContacto;
    }

    public void setTypeContacto(TypeContacto typeContacto) {
        this.typeContacto = typeContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

   

    
    
    
    
}
