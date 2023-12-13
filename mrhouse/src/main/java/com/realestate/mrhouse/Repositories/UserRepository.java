/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Repositories;

import com.realestate.mrhouse.Entities.Users;
import com.realestate.mrhouse.Relations.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 2171584201008
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u where u.email= :email")
    public Users searchByEmail(@Param("email") String email);

    //Optional<Users> findByDniAndRol(String email,Long dni, Rol rol);
}
