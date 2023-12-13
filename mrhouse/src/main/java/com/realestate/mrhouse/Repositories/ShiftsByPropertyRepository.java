/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.ShiftsByProperty;
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
public interface ShiftsByPropertyRepository extends JpaRepository<ShiftsByProperty, Long> {

    @Query("SELECT u FROM ShiftsByProperty u where u.userEmail= :email")
    List<ShiftsByProperty> findByUserEmail(@Param("email") String email);

    // Consulta para obtener todas las ofertas por las propiedades de un publicador por su DNI
    @Query("SELECT o FROM ShiftsByProperty o WHERE o.property.publishers.dni = :dniPublisher")
    List<ShiftsByProperty> findByPublisherDni(@Param("dniPublisher") Long dniPublisher);

}
