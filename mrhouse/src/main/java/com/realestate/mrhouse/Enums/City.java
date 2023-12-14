/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Enums;

/**
 *
 * @author Usuario
 */
public enum City {
    TODOS("TODOS"),
    CORDOBA("Cordoba"),
    ROSARIO("Rosario"),
    MENDOZA("Mendoza"),
    SAN_MIGUEL_DE_TUCUMAN("San Miguel de Tucuman"),
    LA_PLATA("La Plata"),
    MAR_DEL_PLATA("Mar del Plata"),
    SALTA("Salta"),
    SANTA_FE("Santa Fe"),
    SAN_JUAN("San Juan"),
    RESISTENCIA("Resistencia"),
    NEUQUEN("Neuquen"),
    CORRIENTES("Corrientes"),
    SANTIAGO_DEL_ESTERO("Santiago del Estero"),
    POSADAS("Posadas"),
    MERLO("Merlo"),
    PARANA("Parana"),
    FORMOSA("Formosa"),
    SAN_LUIS("San Luis"),
    RAWSON("Rawson"),
    LA_RIOJA("La Rioja"),
    SANTA_ROSA("Santa Rosa"),
    USHUAIA("Ushuaia"),
    VIEDMA("Viedma"),
    JUJUY("Jujuy"),
    CABA("Ciudad de Buenos Aires");
    
    private final String nombre;
    
    City(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
