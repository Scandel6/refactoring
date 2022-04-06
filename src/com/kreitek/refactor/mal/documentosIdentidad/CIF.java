package com.kreitek.refactor.mal.documentosIdentidad;

import com.kreitek.refactor.mal.interfaces.DocumentoIdentidad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CIF implements DocumentoIdentidad {
    private enum TipoUltimoCaracter {LETRA, NUMERO, AMBOS}
    private String numeroCif;

    public CIF (String numeroCif){
        this.numeroCif = numeroCif.toUpperCase();
    }

    @Override
    public boolean validar() {
        if (this.numeroCif != null) {
            final TipoUltimoCaracter tipoUltimoCaracter = asignarTipoUltimoCaracter();
            final int posicionControl = obtenerPosicionCaracterControl();

            if (!esPrimerCaracterValido() ||!cumplePatron() ||!esUltimoCaracterCorrecto()) {
                return false;
            }

            return validarUltimoCaracter(posicionControl, tipoUltimoCaracter);
        }
        return false;
    }

    private boolean validarUltimoCaracter(int posicionCaracterControl, TipoUltimoCaracter tipoUltimoCaracter) {
        final char caracterControl = "JABCDEFGHI".charAt(posicionCaracterControl);
        final char ultimoCaracter = this.numeroCif.charAt(this.numeroCif.length() - 1);

        if (tipoUltimoCaracter == TipoUltimoCaracter.NUMERO) {
            if(!validarTipoNumero(ultimoCaracter, posicionCaracterControl)){
                return false;
            }

        } else if (tipoUltimoCaracter == TipoUltimoCaracter.LETRA) {
            if(!validarTipoLetra(ultimoCaracter,caracterControl)){
                return false;
            }
        } else {
            if(!validarTipoAmbos(ultimoCaracter,posicionCaracterControl, caracterControl)){
                return false;
            }
        }
        return true;
    }

    private boolean validarTipoAmbos(char ultimoCaracter, int posicionCaracterControl, char caracterControl) {
        int indiceUltimoCaracter = "JABCDEFGHI".indexOf(ultimoCaracter);
        if (indiceUltimoCaracter < 0) {
            indiceUltimoCaracter = Integer.parseInt(Character.toString(ultimoCaracter));
            if (posicionCaracterControl != indiceUltimoCaracter) {
                return false;
            }
        }
        if ((posicionCaracterControl != indiceUltimoCaracter) && (caracterControl != ultimoCaracter)) {
            return false;
        }
        return true;
    }

    private boolean validarTipoLetra(char ultimoCaracter, char  caracterControl) {
        return caracterControl != ultimoCaracter;
    }

    private boolean validarTipoNumero(char ultimoCaracter, int posicionCaracterControl) {
        final int ultCar = Integer.parseInt(Character.toString(ultimoCaracter));
        return posicionCaracterControl== ultCar;
    }

    private int obtenerPosicionCaracterControl() {
        String digitos = this.numeroCif.substring(1, this.numeroCif.length() - 1);

        int sumaPares = 0;
        for (int i = 1; i <= digitos.length() - 1; i = i + 2) {
            sumaPares += Integer.parseInt(digitos.substring(i, i + 1));
        }

        int sumaImpares = 0;
        for (int i = 0; i <= digitos.length() - 1; i = i + 2) {
            Integer cal = Integer.parseInt(digitos.substring(i, i + 1)) * 2;
            if (cal.toString().length() > 1) {
                cal = Integer.parseInt(cal.toString().substring(0, 1))
                        + Integer.parseInt(cal.toString().substring(1, 2));
            }
            sumaImpares += cal;
        }

        int sumaTotal = sumaPares + sumaImpares;
        int numControl = 10 - (sumaTotal % 10);
        return numControl == 10 ? 0 : numControl;
    }

    private boolean esUltimoCaracterCorrecto() {
        final char primerCar = this.numeroCif.charAt(0);
        final char ultimoCar = this.numeroCif.charAt(this.numeroCif.length() - 1);

        if (primerCar == 'P' || primerCar == 'Q' || primerCar == 'S' || primerCar == 'K' || primerCar == 'W') {
            if (!(ultimoCar >= 'A' && ultimoCar <= 'Z')) {
                return false;
            }
        } else if (primerCar == 'A' || primerCar == 'B' || primerCar == 'E' || primerCar == 'H') {
            if (!(ultimoCar >= '0' && ultimoCar <= '9')) {
                return false;
            }
        }
        return true;

    }

    private TipoUltimoCaracter asignarTipoUltimoCaracter() {
        char primerCar = this.numeroCif.charAt(0);
        char ultimoCar = this.numeroCif.charAt(this.numeroCif.length() - 1);

        if (primerCar == 'P' || primerCar == 'Q' || primerCar == 'S' || primerCar == 'K' || primerCar == 'W') {
            return TipoUltimoCaracter.LETRA;
        } else if (primerCar == 'A' || primerCar == 'B' || primerCar == 'E' || primerCar == 'H') {
            return TipoUltimoCaracter.NUMERO;
        } else {
            return TipoUltimoCaracter.AMBOS;
        }
    }

    private boolean cumplePatron() {
        Pattern mask = Pattern.compile("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z[0-9]]{1}");
        Matcher matcher = mask.matcher(this.numeroCif);
        return matcher.matches();
    }

    private boolean esPrimerCaracterValido() {
        return "ABCDEFGHJKLMNPQRSUVW".indexOf(this.numeroCif.charAt(0)) != -1;
    }
}
