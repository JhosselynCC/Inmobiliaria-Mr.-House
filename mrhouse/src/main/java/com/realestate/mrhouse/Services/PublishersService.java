/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Enums.TypeOwner;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.PublishersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2171584201008
 */
@Service
public class PublishersService {

    @Autowired
    PublishersRepository publishersRepository;

    @Transactional
    public void createPublishersService(String name, String typeOwner) throws MyException {
        validar(name);
        Publishers publishers = new Publishers();

        publishers.setName(name);
 
        publishers.setTypeOwner(TypeOwner.valueOf(typeOwner));

        publishersRepository.save(publishers);
    }

    public List<Publishers> listPublishers() {
        List<Publishers> publishers = new ArrayList();
        publishers = publishersRepository.findAll();
        return publishers;
    }
    


    public void EditPublishers(String name, String id) throws MyException {
        validar(name);

        Optional<Publishers> respuesta = publishersRepository.findById(id);

        if (respuesta.isPresent()) {
            Publishers a = respuesta.get();

            a.setName(name);

            publishersRepository.save(a);

        }

    }

      public Publishers getOne(String id){
        return publishersRepository.getOne(id);
    }
    private void validar(String name) throws MyException {
        if (name == null) {
            throw new MyException("El nombre de la inmobiliaria no puede estar vacio");
        }

    }
}
