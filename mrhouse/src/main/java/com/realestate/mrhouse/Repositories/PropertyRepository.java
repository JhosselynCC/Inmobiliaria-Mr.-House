/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.Property;
import com.realestate.mrhouse.Enums.City;
import com.realestate.mrhouse.Enums.TypeProperty;
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

    // Consulta para obtener todas las propiedades de un publicador por su DNI
    @Query("SELECT p FROM Property p WHERE p.publishers.dni = :dni")
    List<Property> findPropertiesByPublisherDni(@Param("dni") Long dni);

    // Consulta para obtener todas las propiedades de alquiler con filtros
    @Query("SELECT p FROM Property p WHERE "
            + "(:city IS NULL OR p.city = :city OR :city = 'TODOS') AND "
            + "(:type IS NULL OR p.typeProperty = :type OR :type = 'TODOS') AND"
            + "(:price IS NULL OR p.price <= :price) AND "
            + "p.typePublication = 'ALQUILER'")
    List<Property> findPropertiesByCityAndTypeAndPriceAlquiler(
            @Param("city") City city,
            @Param("type") TypeProperty type,
            @Param("price") Double price
    );
    

    // Consulta para obtener todas las propiedades de alquiler con filtros
    @Query("SELECT p FROM Property p WHERE "
            + "(:city IS NULL OR p.city = :city OR :city = 'TODOS') AND "
            + "(:type IS NULL OR p.typeProperty = :type OR :type = 'TODOS') AND"
            + "(:price IS NULL OR p.price <= :price) AND "
            + "p.typePublication = 'VENTA'")
    List<Property> findPropertiesByCityAndTypeAndPriceCompra(
            @Param("city") City city,
            @Param("type") TypeProperty type,
            @Param("price") Double price
    );

    // Consulta para obtener las ultimas publicaciones por tipo de publicacion se limira a que muestre 2 por tipo
    @Query("SELECT p FROM Property p WHERE p.typePublication = 'ALQUILER' ORDER BY p.alta DESC")
    List<Property> findLatestAlquilerProperties();

    @Query("SELECT p FROM Property p WHERE p.typePublication = 'VENTA' ORDER BY p.alta DESC")
    List<Property> findLatestVentaProperties();

    @Query("SELECT p FROM Property p WHERE p IN :properties ORDER BY p.alta DESC")
    List<Property> findLatestProperties(@Param("properties") List<Property> properties);
}
