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
public enum Province {
    BUENOS_AIRES("Buenos Aires"),
    CORDOBA("Córdoba"),
    ROSARIO("Rosario"),
    MENDOZA("Mendoza"),
    SAN_MIGUEL_DE_TUCUMAN("San Miguel de Tucumán"),
    LA_PLATA("La Plata"),
    MAR_DEL_PLATA("Mar del Plata"),
    SALTA("Salta"),
    SANTA_FE("Santa Fe"),
    SAN_JUAN("San Juan"),
    RESISTENCIA("Resistencia"),
    NEUQUEN("Neuquén"),
    CORRIENTES("Corrientes"),
    SANTIAGO_DEL_ESTERO("Santiago del Estero"),
    POSADAS("Posadas"),
    MERLO("Merlo"),
    PARANA("Paraná"),
    FORMOSA("Formosa"),
    SAN_LUIS("San Luis"),
    RAWSON("Rawson"),
    LA_RIOJA("La Rioja"),
    SANTA_ROSA("Santa Rosa"),
    USHUAIA("Ushuaia"),
    VIEDMA("Viedma"),
    JUJUY("Jujuy");

    private final String nombre;

    Province(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}