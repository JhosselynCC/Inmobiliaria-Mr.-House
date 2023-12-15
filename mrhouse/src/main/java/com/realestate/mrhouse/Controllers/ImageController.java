/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Controllers;

import com.realestate.mrhouse.Entities.Image;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Repositories.ImageRepository;
import com.realestate.mrhouse.Services.ImageService;
import com.realestate.mrhouse.Services.PropertyService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author 2171584201008
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/photos/{id}")

    public ResponseEntity<byte[]> imageProperty(@PathVariable String id) {
        byte[] image = imageService.imgToBite(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable String id) {
        Optional<Image> imageOptional = imageRepository.findById(id);

        if (imageOptional.isPresent()) {
            byte[] image = imageOptional.get().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo de imagen
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/view/principal/{imageId}/{imageId1}")
    public ResponseEntity<byte[]> viewImagePrincipal(@PathVariable String imageId, @PathVariable String imageId1) {
        // Comprobación de idprop
        Optional<Image> image1Optional = imageRepository.findById(imageId1);

        // Resto de tu lógica para obtener y devolver la imagen
        Optional<Image> imageOptional = imageRepository.findById(imageId);

        if (imageOptional.isPresent()) {
            byte[] image = imageOptional.get().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo de imagen

            // Registros para depuración
            System.out.println("Imagen cargada correctamente: " + imageId);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);

        } else {

            if (image1Optional.isPresent()) {

                byte[] image = image1Optional.get().getContenido();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo de imagen

                // Registros para depuración
                System.out.println("Imagen cargada correctamente: " + imageId);

                return new ResponseEntity<>(image, headers, HttpStatus.OK);

            } else {

                // Registros para depuración
                System.out.println("Imagen no encontrada: " + imageId);

                // Manejar el caso donde la propiedad no se encuentra
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        }

    }

    @PostMapping("/view/select/{id}")
    public String selectImagePrincipal(@PathVariable String id, @RequestParam String imageId, @RequestParam String imageId1) {

        viewImagePrincipal(imageId, imageId1);


        return "redirect:/alquilar/property/" + id;
    }
    

}
