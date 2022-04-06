package com.kreitek.refactor.mal;

import com.kreitek.refactor.mal.documentosIdentidad.CIF;
import com.kreitek.refactor.mal.documentosIdentidad.DNI;
import com.kreitek.refactor.mal.documentosIdentidad.NIE;
import com.kreitek.refactor.mal.interfaces.DocumentoIdentidad;

class  Main
{
    public static void main(String args[])
    {
        System.out.println("=====================");
        System.out.println("Vamos a refactorizar!");
        System.out.println("=====================");

        // creamos un DNI correcto
        DocumentoIdentidad dniCorrecto = new DNI("11111111H");
        boolean esValidoDniCorrecto = dniCorrecto.validar();
        System.out.println("DNI " + dniCorrecto.getNumero() + " es: " + esValidoDniCorrecto);

        // creamos un DNI incorrecto
        DocumentoIdentidad dniIncorrecto = new DNI("24324356A");
        boolean esValidoDniIncorrecto = dniIncorrecto.validar();
        System.out.println("DNI " + dniIncorrecto.getNumero() + " es: " + esValidoDniIncorrecto);

        // creamos un NIE correcto
        DocumentoIdentidad nieCorrecto = new NIE("X0932707B");
        boolean esValidoNieCorrecto = nieCorrecto.validar();
        System.out.println("NIE " + nieCorrecto.getNumero() + " es: " + esValidoNieCorrecto);

        // creamos un NIE incorrecto
        DocumentoIdentidad nieIncorrecto = new NIE("Z2691139Z");
        boolean esValidoNieIncorrecto = nieIncorrecto.validar();
        System.out.println("NIE " + nieIncorrecto.getNumero() + " es: " + esValidoNieIncorrecto);

        // creamos un CIF correcto
        DocumentoIdentidad cifCorrecto = new CIF("W9696294I");
        boolean esValidoCifCorrecto = cifCorrecto.validar();
        System.out.println("CIF " + cifCorrecto.getNumero() + " es: " + esValidoCifCorrecto);

        // creamos un CIF incorrecto
        DocumentoIdentidad cifIncorrecto = new CIF("W9696294A");
        boolean esValidoCifIncorrecto = cifIncorrecto.validar();
        System.out.println("CIF " + cifIncorrecto.getNumero() + " es: " + esValidoCifIncorrecto);

    }
}