package com.example.vale.farmacia;

public class farmacia {

    int id;
    String nome;
    String indirizzo;
    String chisiamo;
    String telefono;
    String latitutdine;
    String longitudine;

    /*
    * Costruttore
    */
    public farmacia(int id, String nome, String indirizzo, String chisiamo, String telefono, String latitutdine, String longitudine) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.chisiamo = chisiamo;
        this.telefono = telefono;
        this.latitutdine = latitutdine;
        this.longitudine = longitudine;
    }

    /*
    * Metodi getter /setter
    */

    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getChisiamo() {
        return chisiamo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getLatitutdine() {
        return latitutdine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
