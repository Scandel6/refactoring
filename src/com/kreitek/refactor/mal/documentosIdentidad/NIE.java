package com.kreitek.refactor.mal.documentosIdentidad;

public class NIE extends DocumentoIdentidad {

    public NIE(String numeroNie) {
       super(numeroNie);
    }

    @Override
    public boolean validar() {
        final char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        boolean esPrimerCaracterX = this.numero.charAt(0) == 'X';
        boolean esPrimerCaracterY = this.numero.charAt(0) == 'Y';
        boolean esPrimerCaracterZ = this.numero.charAt(0) == 'Z';

        boolean esValido = tieneFormatoCorrecto(esPrimerCaracterX, esPrimerCaracterY, esPrimerCaracterZ);

        String copianumero = anadirPrefijoNumericoNie(esValido, esPrimerCaracterX, esPrimerCaracterY, esPrimerCaracterZ);

        if (esValido) {
            char letra = Character.toUpperCase(copianumero.charAt(8));
            int minumero = Integer.parseInt(copianumero.substring(1, 8));
            esValido = (letra == asignacionLetra[minumero % 23]);
        }

        return esValido;
    }

    private String anadirPrefijoNumericoNie(boolean esValido, boolean esPrimerCaracterX, boolean esPrimerCaracterY, boolean esPrimerCaracterZ) {
        String copiaNieConPrefijo = this.numero;
        if (esValido && esPrimerCaracterX) {
            copiaNieConPrefijo = "0" + this.numero.substring(1, 9);
        } else if (esValido && esPrimerCaracterY) {
            copiaNieConPrefijo = "1" + this.numero.substring(1, 9);
        } else if (esValido && esPrimerCaracterZ) {
            copiaNieConPrefijo = "2" + this.numero.substring(1, 9);
        }
        return copiaNieConPrefijo;
    }

    private boolean tieneFormatoCorrecto(boolean esPrimerCaracterX, boolean esPrimerCaracterY, boolean esPrimerCaracterZ) {
        boolean esValido = false;
        if (this.numero.length() == 9 && Character.isLetter(this.numero.charAt(8))
                && esPrimerCaracterX || esPrimerCaracterY || esPrimerCaracterZ) {
            int i = 1;
            do {
                int caracterASCII = this.numero.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } while (i < this.numero.length() - 1 && esValido);
        }
        return esValido;
    }

}
