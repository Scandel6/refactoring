package com.kreitek.refactor.mal.documentosIdentidad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CIF extends DocumentoIdentidad {
    private enum TipoUltimoCaracter {LETRA, NUMERO, AMBOS}

    public CIF(String numeroCif) {
        super(numeroCif);
    }

    @Override
    public boolean validar() {
        if (this.numero != null) {
            final char primerCar = this.numero.charAt(0);
            final char ultimoCar = this.numero.charAt(this.numero.length() - 1);

            final boolean esUltimoCaracterLetra = primerCar == 'P' || primerCar == 'Q' || primerCar == 'S' || primerCar == 'K' || primerCar == 'W';
            final boolean esUltimoCaracterNumero = primerCar == 'A' || primerCar == 'B' || primerCar == 'E' || primerCar == 'H';

            final TipoUltimoCaracter tipoUltimoCaracter = asignarUltimoCaracter(esUltimoCaracterLetra, esUltimoCaracterNumero);

            final int posicionControl = obtenerPosicionCaracterControl();

            if (!esPrimerCaracterValido() || !cumplePatron() || !esValidoUltimoCaracter(ultimoCar, esUltimoCaracterLetra, esUltimoCaracterNumero)) {
                return false;
            }

            return validarUltimoCaracter(posicionControl, tipoUltimoCaracter);
        }
        return false;
    }

    private TipoUltimoCaracter asignarUltimoCaracter(boolean esUltimoCaracterLetra, boolean esUltimoCaracterNumero) {
        if (esUltimoCaracterLetra) {
            return TipoUltimoCaracter.LETRA;
        } else if (esUltimoCaracterNumero) {
            return TipoUltimoCaracter.NUMERO;
        }
        return TipoUltimoCaracter.AMBOS;
    }

    private boolean esValidoUltimoCaracter(char ultimoCar, boolean esUltimoCaracterLetra, boolean esUltimoCaracterNumero) {
        if (esUltimoCaracterLetra) {
            if (!(ultimoCar >= 'A' && ultimoCar <= 'Z')) {
                return false;
            }
        } else if (esUltimoCaracterNumero) {
            if (!(ultimoCar >= '0' && ultimoCar <= '9')) {
                return false;
            }
        }
        return true;
    }

    private boolean validarUltimoCaracter(int posicionCaracterControl, TipoUltimoCaracter tipoUltimoCaracter) {
        final char caracterControl = "JABCDEFGHI".charAt(posicionCaracterControl);
        final char ultimoCaracter = this.numero.charAt(this.numero.length() - 1);

        if (tipoUltimoCaracter == TipoUltimoCaracter.NUMERO) {
            if (!validarTipoNumero(ultimoCaracter, posicionCaracterControl)) {
                return false;
            }

        } else if (tipoUltimoCaracter == TipoUltimoCaracter.LETRA) {
            if (!validarTipoLetra(ultimoCaracter, caracterControl)) {
                return false;
            }
        } else {
            if (!validarTipoAmbos(ultimoCaracter, posicionCaracterControl, caracterControl)) {
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

    private boolean validarTipoLetra(char ultimoCaracter, char caracterControl) {
        return caracterControl == ultimoCaracter;
    }

    private boolean validarTipoNumero(char ultimoCaracter, int posicionCaracterControl) {
        final int ultCar = Integer.parseInt(Character.toString(ultimoCaracter));
        return posicionCaracterControl == ultCar;
    }

    private int obtenerPosicionCaracterControl() {
        String digitos = this.numero.substring(1, this.numero.length() - 1);

        int sumaPares = 0;
        for (int i = 1; i <= digitos.length() - 1; i = i + 2) {
            sumaPares += Integer.parseInt(digitos.substring(i, i + 1));
        }

        int sumaImpares = 0;
        for (int i = 0; i <= digitos.length() - 1; i = i + 2) {
            int cal = Integer.parseInt(digitos.substring(i, i + 1)) * 2;
            if (Integer.toString(cal).length() > 1) {
                cal = Integer.parseInt(Integer.toString(cal).substring(0, 1))
                        + Integer.parseInt(Integer.toString(cal).substring(1, 2));
            }
            sumaImpares += cal;
        }

        int sumaTotal = sumaPares + sumaImpares;
        int numControl = 10 - (sumaTotal % 10);
        return numControl == 10 ? 0 : numControl;
    }

    private boolean cumplePatron() {
        Pattern mask = Pattern.compile("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z[0-9]]");
        Matcher matcher = mask.matcher(this.numero);
        return matcher.matches();
    }

    private boolean esPrimerCaracterValido() {
        return "ABCDEFGHJKLMNPQRSUVW".indexOf(this.numero.charAt(0)) != -1;
    }
}
