package com.example.vale.farmacia;
import android.content.Context;
import java.util.LinkedList;
import java.util.List;

public class riepilogoOrdine{


    public static List<farmaco> elenco = new LinkedList<farmaco>();
    public static ListAdapter listaRiepilogo;

    public static ListAdapter getListAdapter(Context ctx, ListAdapterListener listener){
        if(listaRiepilogo == null){
            listaRiepilogo = new ListAdapter(ctx,R.layout.itemlistrow, elenco, listener);
            return listaRiepilogo;
        }
        return listaRiepilogo;
    }

    public static ListAdapter getListaRiepilogo() {
        return listaRiepilogo;
    }

    public static void setListaRiepilogo(ListAdapter listaRiepilogo) {
        riepilogoOrdine.listaRiepilogo = listaRiepilogo;
    }


    public static List<farmaco> getElenco() {
        return elenco;
    }

    public static void setElenco(List<farmaco> elenco) {
        riepilogoOrdine.elenco = elenco;
    }
}
