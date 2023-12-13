/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Image;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Entities.Publishers;
import com.realestate.mrhouse.Enums.City;
import com.realestate.mrhouse.Enums.Province;
import com.realestate.mrhouse.Enums.StatusProperty;
import com.realestate.mrhouse.Enums.TypeProperty;
import com.realestate.mrhouse.Enums.TypePublication;

import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.ImageRepository;
import com.realestate.mrhouse.Repositories.PropertyRepository;
import com.realestate.mrhouse.Repositories.PublishersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    @Autowired
    private ImageRepository imageRepository;
    
    

    @Autowired
    public PropertyService(PropertyRepository propertyRepository,  ImageRepository  imageRepository) {
        this.propertyRepository = propertyRepository;
        this.imageRepository = imageRepository;
    }

    
    
    @Transactional
    public void createProperty(List<MultipartFile> images, String typePublication, String title, String typeProperty, String features, Double price, String location, String province, String city, Long idPublisher) throws MyException {

        validar(typePublication, title, typeProperty, features, price, images, location, province, city, idPublisher);

        Publishers p = publisherRepository.findById(idPublisher).get();
                
        /*   autor a = autorRepositorio.findById(IdAutor).get();
                .orElseThrow(() -> new MyException("Publisher no encontrado con ID: " + idPublisher));
         */

        Property property = new Property();

        property.setTypePublication(TypePublication.valueOf(typePublication));

        property.setTitle(title);
        property.setTypeProperty(TypeProperty.valueOf(typeProperty));
        property.setProvince(Province.valueOf(province));
        property.setCity(City.valueOf(city));
        property.setFeatures(features);
        property.setPrice(price);
        /*
        property.setImages((List<Image>) images);
         */
        for (MultipartFile image : images) {
            imageService.save(image, property);
        }
        property.setLocation(location);
        property.setAlta(new Date());
        property.setPublishers(p);
        property.setStatusProperty(StatusProperty.DISPONIBLE);
        property.setActive(true);

        propertyRepository.save(property);
    }

    
    /*
    
    */
    public List<Property> listProperties() {

        /* public List<Property> listProperties() {
        List<Property> properties = new ArrayList();   
        properties = propertyRepository.findAll();
        return properties;
    }
         */
        
        /*
        List<Property> properties = new ArrayList();

        for (Property property : properties) {
            // Fetch images for each property and set them
            // List<Object[]>imageInfoList=propertyRepository.searchImageByProperty(property.getId());
            List<Object[]> imageInfoList = (List<Object[]>) propertyRepository.searchImageByProperty(property.getId());
            List<Image> images = new ArrayList<>();

            for (Object[] imageInfo : imageInfoList) {
                Long imageId = (Long) imageInfo[0];
                String contenido = (String) imageInfo[1];
                // Assuming you have an Image class
                Image image = new Image(imageId, contenido);
                images.add(image);

            }

        }

        properties = propertyRepository.findAll();

        return properties;*/
        
            
        return propertyRepository.findAll();
    }

    public List<Image> obtainImageByProperty(String propertyId) {
        return imageRepository.findByPropertyId(propertyId);
    }

    
    
    /*
    *Funciones para Alquilar
    */
    
        public List<Property> listAlquiler() {
        List<Property> properties = listProperties();

        List<Property> alquileres = new ArrayList<>();

        Iterator<Property> it = properties.iterator();

        while (it.hasNext()) {

            Property aux = it.next();

            if (aux.getTypePublication().equals(TypePublication.ALQUILER)) {
                alquileres.add(aux);
            }

        }

        return alquileres;
    }

    public List<Property> listAlquiler3(Long id) {

        int iterar = 0;

        List<Property> alquileres3 = new ArrayList<>();

        List<Property> alquileres = listAlquiler();

        Iterator<Property> it = alquileres.iterator();

        while (it.hasNext()) {
            Property aux = it.next();


            if (aux.getId() != id) {
                
                iterar += 1;

                if (iterar <= 3) {

                    alquileres3.add(aux);

                }

            }

        }

        return alquileres3;
    }
    
    
    
    /*
    *Funciones para Comprar
    */
    
        public List<Property> listComprar() {
        List<Property> properties = listProperties();

        List<Property> comprar = new ArrayList<>();

        Iterator<Property> it = properties.iterator();

        while (it.hasNext()) {

            Property aux = it.next();

            if (aux.getTypePublication().equals(TypePublication.VENTA)) {
                comprar.add(aux);
            }

        }

        return comprar;
    }

    public List<Property> listComprar3(Long id) {

        int iterar = 0;

        List<Property> comprar3 = new ArrayList<>();

        List<Property> compras = listComprar();

        Iterator<Property> it = compras.iterator();

        while (it.hasNext()) {
            Property aux = it.next();


            if (aux.getId() != id) {
                
                iterar += 1;

                if (iterar <= 3) {

                    comprar3.add(aux);

                }

            }

        }

        return comprar3;
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
    public Property getOne(Long id) {
        return propertyRepository.getOne(id);
    }

    private void validar(String typePublication, String title, String typeProperty, String features, Double price, List<MultipartFile> images, String location, String province, String city, Long idPublisher) throws MyException {
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
        if (province == null) {
            throw new MyException("La provincia no puede ser nula");
        }
        if (city == null) {
            throw new MyException("La ciudad no puede ser nula");
        }
        if (idPublisher == null) {
            throw new MyException("El Id del Publisher no puede ser nulo o estar vacio");
        }

    }
}
