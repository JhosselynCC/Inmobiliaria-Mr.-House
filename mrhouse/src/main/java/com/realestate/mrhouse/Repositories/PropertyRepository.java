/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.Property;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 2171584201008
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT l FROM Property l WHERE l.title = :title")
    public Property searchbyProperty(@Param("title") String title);

    @Query("SELECT p.id, i.contenido FROM Property p INNER JOIN Image i ON p.id = i.property.id WHERE p.id = :id")
    public Property searchImageByProperty(@Param("id") Long id);

}


