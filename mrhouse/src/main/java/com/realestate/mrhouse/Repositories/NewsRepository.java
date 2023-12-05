/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emilio David Ibarra
 */

@Repository
public interface NewsRepository extends JpaRepository<News, String>{
    @Query ("SELECT n FROM News n WHERE n.titulo = :titulo") 
    public News buscarPorTitulo (@Param ("titulo") String titulo);
    
    @Query ("SELECT n FROM News n WHERE n.texto = :texto") 
    public News buscarPorTexto (@Param ("texto") String texto);
    
    
}
