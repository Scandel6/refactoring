package com.kreitek.refactor.mal.documentosIdentidad;

import com.kreitek.refactor.mal.interfaces.DocumentoIdentidad;

public class DNI implements DocumentoIdentidad {
    private String numeroDni;

    public DNI(String numeroDni) {
        this.numeroDni = numeroDni.toUpperCase();
    }

    @Override
    public boolean validar() {
        String posiblesValoresLetra = "TRWAGMYFPDXBNJZSQVHLCKE";
        String parteNumerica = this.numeroDni.trim().replaceAll(" ", "").substring(0, 8);
        char letraDni = this.numeroDni.charAt(8);
        int controlDni = Integer.parseInt(parteNumerica) % 23;

        return this.numeroDni.length() == 9
                || isNumeric(parteNumerica)
                || posiblesValoresLetra.charAt(controlDni) == letraDni;
    }

    private boolean isNumeric(String parteNumerica) {
        return parteNumerica != null && parteNumerica.matches("[0-9]+");
    }
}
