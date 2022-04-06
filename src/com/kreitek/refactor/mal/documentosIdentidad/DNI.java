package com.kreitek.refactor.mal.documentosIdentidad;

public class DNI extends DocumentoIdentidad {

    public DNI(String numeroDNI) {
        super(numeroDNI);
    }

    @Override
    public boolean validar() {
        String posiblesValoresLetra = "TRWAGMYFPDXBNJZSQVHLCKE";
        String parteNumerica = this.numero.trim().replaceAll(" ", "").substring(0, 8);
        char letraDni = this.numero.charAt(8);
        int controlDni = Integer.parseInt(parteNumerica) % 23;

        return this.numero.length() == 9
                && isNumeric(parteNumerica)
                && posiblesValoresLetra.charAt(controlDni) == letraDni;
    }

    private boolean isNumeric(String parteNumerica) {
        return parteNumerica != null && parteNumerica.matches("[0-9]+");
    }
}
