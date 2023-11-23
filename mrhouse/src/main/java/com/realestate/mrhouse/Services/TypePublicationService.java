/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.TypePublication;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.TypePublicationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import javax.transaction.Transactional;

/**
 *
 * @author 2171584201008
 */
@Service
public class TypePublicationService {

    @Autowired
    private TypePublicationRepository typePublicationRepository;

    @Transactional
    public void CreateTypePublication(String name) throws MyException {
        validacion(name);
        TypePublication a = new TypePublication();

        a.setName(name);

        typePublicationRepository.save(a);
    }

    public List<TypePublication> ListTypePublications() {

        List<TypePublication> TypePublications = new ArrayList();
        TypePublications = typePublicationRepository.findAll();

        return TypePublications;
    }

    public void EditTypePublications(String name, String id) throws MyException {
        validacion(name);
        Optional<TypePublication> respuesta = typePublicationRepository.findById(id);

        if (respuesta.isPresent()) {
            TypePublication a = respuesta.get();

            a.setName(name);

            typePublicationRepository.save(a);

        }

    }

    public TypePublication getOne(String id) {
        return typePublicationRepository.getOne(id);
    }

    private void validacion(String name) throws MyException {
        if (name == null) {
            throw new MyException("El nombre del tipo de publicacion no puede estar vacio");
        }

    }

}
