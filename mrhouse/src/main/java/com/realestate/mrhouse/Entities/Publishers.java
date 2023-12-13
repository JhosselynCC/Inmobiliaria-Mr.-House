/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Entities;

import com.realestate.mrhouse.Enums.TypeOwner;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author 2171584201008
 */
@Entity
public class Publishers {

   /* @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;*/

    @Id
    private Long dni;
    private String name;

    @Enumerated(EnumType.STRING)
    private TypeOwner typeOwner;

    public Publishers() {
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOwner getTypeOwner() {
        return typeOwner;
    }

    public void setTypeOwner(TypeOwner typeOwner) {
        this.typeOwner = typeOwner;
    }

}
