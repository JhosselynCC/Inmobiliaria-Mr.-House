/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 2171584201008
 */
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
    
    @ManyToOne
    private TypePublication typePublication;
    
    private String title;
    private String features;
    private Double price;
    private String Images;
    private String location;
    
    @ManyToOne
    private Publishers publishers;
    

    @Temporal(TemporalType.DATE)
    private Date alta;

    public Property() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public TypePublication getTypePublication() {
        return typePublication;
    }

    public void setTypePublication(TypePublication typePublication) {
        this.typePublication = typePublication;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String Images) {
        this.Images = Images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Publishers getPublishers() {
        return publishers;
    }

    public void setPublishers(Publishers publishers) {
        this.publishers = publishers;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

   
        

}
