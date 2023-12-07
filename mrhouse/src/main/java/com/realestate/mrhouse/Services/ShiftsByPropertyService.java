/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.ShiftsByProperty;
import com.realestate.mrhouse.Enums.ShiftStatus;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.PropertyRepository;
import com.realestate.mrhouse.Repositories.ShiftsByPropertyRepository;
import com.realestate.mrhouse.Repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
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
public class ShiftsByPropertyService {

    @Autowired
    private ShiftsByPropertyRepository shiftsByPropertyRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createShiftsByProperty(Long IdProperty, Date startTime) throws MyException {

        /*        public ShiftsByProperty(Long Id, Property property, ShiftStatus shiftStatus, Date startTime, Date endTime, String userEmail, Date dataCreation, boolean active) {
        this.Id = Id;
        this.property = property;
        this.shiftStatus = shiftStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEmail = userEmail;
        this.dataCreation = dataCreation;
        this.active = active;
    }
        
        
         */
        validar(IdProperty, startTime);

        Property p = propertyRepository.findById(IdProperty).get();
        ShiftsByProperty shiftsByProperty = new ShiftsByProperty();

        shiftsByProperty.setProperty(p);
        shiftsByProperty.setStartTime(startTime);

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            shiftsByProperty.setUserEmail(userDetails.getUsername());
            //getUsername());
            // getId());  // Asume que tu UserDetails tiene un método getId()
        } else {
            // Manejar el caso en el que no se puede obtener el usuario autenticado
            throw new MyException("Necesitas estar registrado para poder realizar esta operacion.");
        }
        shiftsByProperty.setShiftStatus(ShiftStatus.RESERVADO);

        shiftsByPropertyRepository.save(shiftsByProperty);

    }

    public List<ShiftsByProperty> ListShiftsByProperty() {

        List<ShiftsByProperty> shiftsByProperty = new ArrayList();

        return shiftsByPropertyRepository.findAll();
    }

    @Transactional
    public void modifyShiftsByProperty(Long Id, Long IdProperty, Date startTime, String userEmail, String shiftStatus) throws MyException {

        
        validationModify(Id, IdProperty, startTime, userEmail, shiftStatus);

        Optional<ShiftsByProperty> reply = shiftsByPropertyRepository.findById(Id);
        Optional<Property> replyProperty = propertyRepository.findById(IdProperty);
        //Optional<Users> replyUsers = userRepository.findById(Id)

        Property p = new Property();

        if (replyProperty.isPresent()) {
            p = replyProperty.get();
        }

        if (reply.isPresent()) {

            ShiftsByProperty shiftsByProperty = reply.get();
            
            shiftsByProperty.setProperty(p);
            shiftsByProperty.setStartTime(startTime);
            shiftsByProperty.setUserEmail(userEmail);
            shiftsByProperty.setShiftStatus(ShiftStatus.valueOf(shiftStatus));
            
            
            shiftsByPropertyRepository.save(shiftsByProperty);
                    

        }

    }

    public ShiftsByProperty getOne(Long Id) {
        return shiftsByPropertyRepository.getOne(Id);
    }

    private void validar(Long IdProperty, Date startTime) throws MyException {
        if (IdProperty == null) {
            throw new MyException("El Id de la propiedad no puede ser nulo");
        }
        if (startTime == null) {
            throw new MyException("El mensaje no puede ser nulo o estar vacio");
        }

    }

    private void validationModify(Long Id, Long IdProperty, Date startTime, 
            String userEmail, String shiftStatus) throws MyException {
        if (Id == null) {
            throw new MyException("El Id de la oferta no puede ser nulo");
        }
        if (IdProperty == null) {
            throw new MyException("El Id de la propiedad no puede ser nulo");
        }
        if (startTime == null) {
            throw new MyException("La fecha no puede ser nulo");
        }
        if (userEmail.isEmpty() || userEmail == null) {
            throw new MyException("El usuarioEmail no puede ser nulo o estar vacio");
        }
        if (shiftStatus.isEmpty() || shiftStatus == null) {
            throw new MyException("El status de la turno no puede ser nulo o estar vacio");
        }

    }

}
