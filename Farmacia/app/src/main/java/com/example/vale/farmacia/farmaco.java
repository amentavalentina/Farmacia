package com.example.vale.farmacia;

/**
 * Created by Vale on 01/12/2015.
 */
public class farmaco {
    int id;
    String nomeFarmaco;
    int tipoFarmaco;
    int quantitaFarmaco;
    double prezzo;
    int idFarmacia;


    public farmaco(int id, String nomeFarmaco, int quantitaFarmaco, int tipoFarmaco, double prezzo, int idFarmacia) {
        this.id = id;
        this.nomeFarmaco = nomeFarmaco;
        this.quantitaFarmaco = quantitaFarmaco;
        this.tipoFarmaco = tipoFarmaco;
        this.prezzo = prezzo;
        this.idFarmacia = idFarmacia;
    }
    public farmaco( String nomeFarmaco, int quantitaFarmaco, int tipoFarmaco, double prezzo, int idFarmacia) {
        this.nomeFarmaco = nomeFarmaco;
        this.quantitaFarmaco = quantitaFarmaco;
        this.tipoFarmaco = tipoFarmaco;
        this.prezzo = prezzo;
        this.idFarmacia = idFarmacia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    public void setNomeFarmaco(String nomeFarmaco) {
        this.nomeFarmaco = nomeFarmaco;
    }

    public int getQuantitaFarmaco() {
        return quantitaFarmaco;
    }

    public void setQuantitaFarmaco(int quantitaFarmaco) {
        this.quantitaFarmaco = quantitaFarmaco;
    }

    public int getTipoFarmaco() {
        return tipoFarmaco;
    }

    public void setTipoFarmaco(int tipoFarmaco) {
        this.tipoFarmaco = tipoFarmaco;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    @Override
    public String toString(){
        return this.nomeFarmaco;
    }
}

