package com.kreitek.refactor.mal.documentosIdentidad;

public abstract class DocumentoIdentidad {
    protected String numero;

    protected DocumentoIdentidad (String numero){
        this.numero = numero.toUpperCase();
    }

    public abstract boolean validar();

    public String getNumero(){
        return this.numero;
    }
}
