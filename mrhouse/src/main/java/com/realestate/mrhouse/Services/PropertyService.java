/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Image;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Enums.TypeProperty;
import com.realestate.mrhouse.Enums.TypePublication;

import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.PropertyRepository;
import com.realestate.mrhouse.Repositories.PublishersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author 2171584201008
 */
@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PublishersRepository publisherRepository;
    @Autowired
    private ImageService imageService;

    @Transactional
    public void createProperty(MultipartFile images, String typePublication, String title, String typeProperty, String features, Double price, String location, String province, String city, String idPublisher) throws MyException {

        validar(typePublication, title, typeProperty, features, price, images, location, province, city, idPublisher);

        
        Publishers p = publisherRepository.findById(idPublisher).get();
              /*   autor a = autorRepositorio.findById(IdAutor).get();
                .orElseThrow(() -> new MyException("Publisher no encontrado con ID: " + idPublisher));
        */

        Property property = new Property();

        property.setTypePublication(TypePublication.valueOf(typePublication));

        property.setTitle(title);
        property.setTypeProperty(TypeProperty.valueOf(typeProperty));
        property.setFeatures(features);
        property.setPrice(price);
        /*
        property.setImages((List<Image>) images);
        */
        Image image = imageService.save(images);
        property.setImage(image);
        
        property.setLocation(location);
        property.setProvince(province);
        property.setCity(city);
        property.setAlta(new Date());
        property.setPublishers(p);

        propertyRepository.save(property);
    }

    public List<Property> listProperties() {
        List<Property>properties = new ArrayList();

        properties = propertyRepository.findAll();

        return properties;
    }

    /*
    public void editProperty(Long id, String title, String features, Double price, MultipartFile images, String location, String typePublication, Long idPublishers) throws MyException {
        validar(title, features, price, images, location, typePublication, idPublishers);
        Optional<Property> respuesta = propertyRepository.findById(id);
        Optional<Publishers> respuestaPublisher = publisherRepository.findById(idPublishers);
        Publishers publishers = new Publishers();

        if (respuestaPublisher.isPresent()) {

            publishers = respuestaPublisher.get();
        }

        if (respuesta.isPresent()) {

            Property property = respuesta.get();

            property.setTitle(title);

            property.setPublishers(publishers);

            property.getTypePublication();

            property.setPrice(price);

            property.setFeatures(features);

            property.setImages((List<Image>) images);

            propertyRepository.save(property);

        }
    }
     */
    
      public Property getOne(Long id){
        return propertyRepository.getOne(id);
    }
      
    private void validar(String typePublication, String title, String typeProperty, String features, Double price, MultipartFile images, String location, String province, String city, String idPublisher) throws MyException {
        if (typePublication == null) {
            throw new MyException("El tipo de publicacion no puede ser nulo");
        }
        if (title.isEmpty() || title == null) {
            throw new MyException("El titulo no puede ser nulo o estar vacio");
        }
        if (typeProperty == null) {
            throw new MyException("El tipo de propiedad no puede ser nulo");
        }
        if (features.isEmpty() || features == null) {
            throw new MyException("Las caracteristicas no pueden ser nulas o estar vacias");
        }
        if (price.isNaN() || price == null) {
            throw new MyException("El valor no puede ser nulo o estar vacio");
        }
       
        if (images.isEmpty() || images == null) {
            throw new MyException("Las imagenes no pueden ser nulas o vacias");
        }
        if (location.isEmpty() || location == null) {
            throw new MyException("La ubicacion no puede ser nula o estar vacia");
        }
        if (province.isEmpty() || province == null) {
            throw new MyException("La provincia no puede ser nula o estar vacia");
        }
        if (city.isEmpty() || city == null) {
            throw new MyException("La ciudad no puede ser nula o estar vaciao");
        }
        if (idPublisher == null || idPublisher.isEmpty()) {
            throw new MyException("El Id del Publisher no puede ser nulo o estar vacio");
        }

    }
}
