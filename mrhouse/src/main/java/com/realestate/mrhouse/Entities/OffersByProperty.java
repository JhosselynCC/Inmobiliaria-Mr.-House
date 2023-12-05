/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Entities;

import com.realestate.mrhouse.Enums.StatusOffer;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 2171584201008
 */
@Entity
public class OffersByProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Property property;

    private String message;

    private String userEmail;

    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @Enumerated(EnumType.STRING)
    private StatusOffer statusOffer;

    @PrePersist
    protected void onCreate() {
        this.alta = new Date();
    }

    public OffersByProperty() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String useEmail) {
        this.userEmail = useEmail;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public StatusOffer getStatusOffer() {
        return statusOffer;
    }

    public void setStatusOffer(StatusOffer statusOffer) {
        this.statusOffer = statusOffer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

}
