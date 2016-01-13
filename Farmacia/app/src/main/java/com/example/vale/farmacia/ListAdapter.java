package com.example.vale.farmacia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;



/**
 * Created by Vale on 06/12/2015.
 */
public class ListAdapter extends ArrayAdapter<farmaco> {
    private ListAdapterListener listener;
    private List<farmaco> items;

    public ListAdapter(Context context, int textViewResourceId, List<farmaco> items, ListAdapterListener listener) {
        super(context, textViewResourceId, items);
        this.listener = listener;
        this.items = items;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        farmaco f = getItem(position);
        Button bt = (Button) v.findViewById(R.id.bt_eliminaRigaCarrello);
        final List<farmaco> elenco = riepilogoOrdine.getElenco();


        if (f != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.lb_nome);
            TextView tt2 = (TextView) v.findViewById(R.id.lb_dettagli);

            if (tt1 != null) {
                tt1.setText(f.getNomeFarmaco());
            }

            if (tt2 != null) {
                tt2.setText(f.getQuantitaFarmaco() + "  " + f.getPrezzo());
            }

        }
        bt.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.d("POSIZIONEEEEEEEEEE",String.valueOf(position));
                items.remove(position);
                //System.out.println("DOPO RIMOZIONE");
                for(int i = 0; i<elenco.size(); i ++){
                    System.out.println(elenco.get(i).getNomeFarmaco());
                }
                listener.forceNotifyDataSetChanged();
            }
        });
        return v;
    }

}
