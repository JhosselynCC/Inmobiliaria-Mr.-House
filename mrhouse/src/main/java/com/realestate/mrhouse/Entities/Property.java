/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.mrhouse.Enums.City;
import com.realestate.mrhouse.Enums.Province;
import com.realestate.mrhouse.Enums.StatusProperty;
import com.realestate.mrhouse.Enums.TypeProperty;
import com.realestate.mrhouse.Enums.TypePublication;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypePublication typePublication;

    private String title;

    @Enumerated(EnumType.STRING)
    private TypeProperty typeProperty;
    
    @Enumerated(EnumType.STRING)
    private Province province;
    
    @Enumerated(EnumType.STRING)
    private City city;

    private String features;
    private Double price;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> image;

    /*
     @OneToOne
    private Image image;
     */
    private String location;
    @ManyToOne
    private Publishers publishers;

    @Temporal(TemporalType.DATE)
    private Date alta;
    
    //Estos datos fueron agregados para completar 

    @Enumerated(EnumType.STRING)
    private StatusProperty StatusProperty;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OffersByProperty> OffersByProperty = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShiftsByProperty> ShiftsByProperty = new ArrayList<>();

    public Property() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TypeProperty getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(TypeProperty typeProperty) {
        this.typeProperty = typeProperty;
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

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public StatusProperty getStatusProperty() {
        return StatusProperty;
    }

    public void setStatusProperty(StatusProperty StatusProperty) {
        this.StatusProperty = StatusProperty;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = true;
    }

    public List<OffersByProperty> getOffersByProperty() {
        return OffersByProperty;
    }

    public void setOffersByProperty(List<OffersByProperty> OffersByProperty) {
        this.OffersByProperty = OffersByProperty;
    }

    public List<ShiftsByProperty> getShiftsByProperty() {
        return ShiftsByProperty;
    }

    public void setShiftsByProperty(List<ShiftsByProperty> ShiftsByProperty) {
        this.ShiftsByProperty = ShiftsByProperty;
    }

}
