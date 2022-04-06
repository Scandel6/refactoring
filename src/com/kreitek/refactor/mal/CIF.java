package com.kreitek.refactor.mal;

public class CIF implements DocumentoIdentidad{
    private enum TipoUltCaracter {LETRA, NUMERO, AMBOS}
    private String numCif;


    @Override
    public boolean validar() {
        return false;
    }

}
