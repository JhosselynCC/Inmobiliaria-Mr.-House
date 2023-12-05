/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.OffersByProperty;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.Users;
import com.realestate.mrhouse.Enums.StatusOffer;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.OffersByPropertyRepository;
import com.realestate.mrhouse.Repositories.PropertyRepository;
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
public class OffersByPropertyService {

    @Autowired
    private OffersByPropertyRepository offersByPropertyRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createOffersByProperty(Long IdProperty, String message) throws MyException {

        validar(IdProperty, message);

        Property p = propertyRepository.findById(IdProperty).get();
        OffersByProperty offersByProperty = new OffersByProperty();

        offersByProperty.setProperty(p);
        offersByProperty.setMessage(message);

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            offersByProperty.setUserEmail(userDetails.getUsername());
            //getUsername());
            // getId());  // Asume que tu UserDetails tiene un método getId()
        } else {
            // Manejar el caso en el que no se puede obtener el usuario autenticado
            throw new MyException("No se puede obtener el usuario autenticado.");
        }

        offersByProperty.setStatusOffer(StatusOffer.PENDIENTE);

        offersByPropertyRepository.save(offersByProperty);

    }

    public List<OffersByProperty> ListOffersByProperty() {

        List<OffersByProperty> offersByProperties = new ArrayList();

        return offersByPropertyRepository.findAll();
    }

    @Transactional
    public void modifyOffersByProperty(Long Id, Long IdProperty, String message, String userEmail, String statusOffer) throws MyException {

        validationModify(Id, IdProperty, message, userEmail, statusOffer);
 

        
        Optional<OffersByProperty> reply = offersByPropertyRepository.findById(Id);
        Optional<Property> replyProperty = propertyRepository.findById(IdProperty);
        //Optional<Users> replyUsers = userRepository.findById(Id)

        Property p = new Property();

        if (replyProperty.isPresent()) {
            p = replyProperty.get();
        }

        if (reply.isPresent()) {

            OffersByProperty offersByProperty = reply.get();
            offersByProperty.setProperty(p);
            offersByProperty.setMessage(message);
            offersByProperty.setUserEmail(userEmail);
            offersByProperty.setStatusOffer(StatusOffer.valueOf(statusOffer));

            offersByPropertyRepository.save(offersByProperty);

        }

    }

    public OffersByProperty getOne(Long Id) {
        return offersByPropertyRepository.getOne(Id);
    }

    private void validar(Long IdProperty, String message) throws MyException {
        if (IdProperty == null) {
            throw new MyException("El Id de la propiedad no puede ser nulo");
        }
        if (message.isEmpty() || message == null) {
            throw new MyException("El mensaje no puede ser nulo o estar vacio");
        }

    }

    private void validationModify(Long Id, Long IdProperty, String message, String userEmail, String statusOffer) throws MyException {
        if (Id == null) {
            throw new MyException("El Id de la oferta no puede ser nulo");
        }
        if (IdProperty == null) {
            throw new MyException("El Id de la propiedad no puede ser nulo");
        }
        if (message.isEmpty() || message == null) {
            throw new MyException("El mensaje no puede ser nulo o estar vacio");
        }
        if (userEmail.isEmpty() || userEmail == null) {
            throw new MyException("El usuarioEmail no puede ser nulo o estar vacio");
        }
        if (statusOffer.isEmpty() || statusOffer == null) {
            throw new MyException("El status de la oferta no puede ser nulo o estar vacio");
        }

    }

}
