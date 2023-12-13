/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 2171584201008
 */
@Repository
public interface PublishersRepository extends JpaRepository<Publishers, Long>{
    
    
    
}
