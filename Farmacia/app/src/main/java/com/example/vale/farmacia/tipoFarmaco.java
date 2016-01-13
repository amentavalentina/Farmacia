package com.example.vale.farmacia;

/**
 * Created by Vale on 01/12/2015.
 */
public class tipoFarmaco {

    int id;
    String descrizione;
    String obbligoricetta;

    public tipoFarmaco(int id, String descrizione, String obbligoricetta) {
        this.id = id;
        this.descrizione = descrizione;
        this.obbligoricetta = obbligoricetta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getObbligoricetta() {
        return obbligoricetta;
    }

    public void setObbligoricetta(String obbligoricetta) {
        this.obbligoricetta = obbligoricetta;
    }
}
