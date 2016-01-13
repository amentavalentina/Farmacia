package com.example.vale.farmacia;

/**
 * Created by Vale on 26/11/2015.
 */
public class Voto {
    int id;
    String voto;

    public Voto(int id, String voto) {
        this.id = id;
        this.voto = voto;}

    public void setId(int id) {
        this.id = id;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public int getId() {
        return id;
    }

    public String getVoto() {
        return voto;
    }

}
