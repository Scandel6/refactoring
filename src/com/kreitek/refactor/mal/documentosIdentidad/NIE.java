package com.kreitek.refactor.mal;

public class NIE implements DocumentoIdentidad {
    private String numeroNie;

    public NIE(String numeroNie) {
        this.numeroNie = numeroNie.toUpperCase();
    }

    @Override
    public boolean validar() {
        final char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        boolean esPrimerCaracterX = this.numeroNie.charAt(0) == 'X';
        boolean esPrimerCaracterY = this.numeroNie.charAt(0) == 'Y';
        boolean esPrimerCaracterZ = this.numeroNie.charAt(0) == 'Z';

        boolean esValido = tieneFormatoCorrecto(esPrimerCaracterX, esPrimerCaracterY, esPrimerCaracterZ);

        String copiaNumeroNie = anadirPrefijoNumericoNie(esValido, esPrimerCaracterX, esPrimerCaracterY, esPrimerCaracterZ);

        if (esValido) {
            char letra = Character.toUpperCase(copiaNumeroNie.charAt(8));
            int miNumeroNie = Integer.parseInt(copiaNumeroNie.substring(1, 8));
            esValido = (letra == asignacionLetra[miNumeroNie % 23]);
        }

        return esValido;
    }

    private String anadirPrefijoNumericoNie(boolean esValido, boolean esPrimerCaracterX, boolean esPrimerCaracterY, boolean esPrimerCaracterZ) {
        String copiaNieConPrefijo = this.numeroNie;
        if (esValido && esPrimerCaracterX) {
            copiaNieConPrefijo = "0" + this.numeroNie.substring(1, 9);
        } else if (esValido && esPrimerCaracterY) {
            copiaNieConPrefijo = "1" + this.numeroNie.substring(1, 9);
        } else if (esValido && esPrimerCaracterZ) {
            copiaNieConPrefijo = "2" + this.numeroNie.substring(1, 9);
        }
        return copiaNieConPrefijo;
    }

    private boolean tieneFormatoCorrecto(boolean esPrimerCaracterX, boolean esPrimerCaracterY, boolean esPrimerCaracterZ) {
        boolean esValido = false;
        if (this.numeroNie.length() == 9 && Character.isLetter(this.numeroNie.charAt(8))
                && esPrimerCaracterX || esPrimerCaracterY || esPrimerCaracterZ) {
            int i = 1;
            do {
                int caracterASCII = this.numeroNie.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } while (i < this.numeroNie.length() - 1 && esValido);
        }
        return esValido;
    }

}
