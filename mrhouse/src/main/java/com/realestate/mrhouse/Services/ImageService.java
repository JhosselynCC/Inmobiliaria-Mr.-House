/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Services;

import com.realestate.mrhouse.Entities.Image;
import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Exceptions.MyException;
import com.realestate.mrhouse.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author 2171584201008
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image save(MultipartFile images,Property property) throws MyException {
        if (images != null) {
            try {

                Image i = new Image();
                i.setMime(images.getContentType());
                i.setNombre(images.getName());
                i.setContenido(images.getBytes());
                i.setProperty(property);

                return imageRepository.save(i);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }

    public byte[] imgToBite(String id) {
        return imageRepository.findById(id).get().getContenido();
    }
}
