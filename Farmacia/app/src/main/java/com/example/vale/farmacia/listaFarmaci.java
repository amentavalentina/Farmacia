package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import database.dataBaseManager;

/**
 * Created by Vale on 04/12/2015.
 */
public class listaFarmaci extends Activity implements ListAdapterListener{
    dataBaseManager db = new dataBaseManager(this);
    int idFarmacia;
    private static int accesso = -1;
    int a=-1;
    double totale;
    int tipologia = -1;
    com.example.vale.farmacia.ListAdapter customAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_farmaci);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        idFarmacia = intent.getIntExtra("idFarmacia", -1);
        final ListView lvFarmaci = (ListView) this.findViewById(com.example.vale.farmacia.R.id.lvFarmaci);
        final ListView lvRiepilogo = (ListView) this.findViewById(com.example.vale.farmacia.R.id.lvRiepilogo);
        final Button bt_continua = (Button) this.findViewById(R.id.bt_continua);
        final Button bt_home = (Button) this.findViewById(R.id.bt_home);
        riempiListView(lvFarmaci);



        accesso = intent.getIntExtra("accesso",-1);
        //Log.d("AAAAAAAAAAAAAAA", String.valueOf(accesso));
        a = intent.getIntExtra("a",-1);
        //Log.d("AAAAAAAAAAAAAAA", String.valueOf(a));
        if (accesso != -1) {
            int idFarmaco = intent.getIntExtra("idFarmaco", -1);
            String nomeFarmaco = intent.getStringExtra("nomeFarmaco");
            int quantita = intent.getIntExtra("quantita", -1);
            int tipoFarmaco = intent.getIntExtra("tipoFarmaco", -1);
            double costoFarmaci = intent.getDoubleExtra("costoFarmaci", -1);
            int idFarmacia = intent.getIntExtra("idFarmacia", -1);
            intent.getIntExtra("idFarmacia", -1);

            List<farmaco> elenco = riepilogoOrdine.getElenco();
            farmaco f = new farmaco(idFarmaco, nomeFarmaco, quantita, tipoFarmaco, costoFarmaci, idFarmacia);
            elenco.add(f);
            if (f.getTipoFarmaco() == 0 || f.getTipoFarmaco() == 1){
                tipologia = 1;
            }
            riepilogoOrdine.setElenco(elenco);

            this.customAdapter = riepilogoOrdine.getListAdapter(this, this);
            lvRiepilogo.setAdapter(customAdapter);
            riepilogoOrdine.setListaRiepilogo(customAdapter);


            System.out.println("PRIMA DELLA RIMOZIONE");
            for(int i = 0; i<elenco.size(); i ++){
                System.out.println(elenco.get(i).getNomeFarmaco());
            }

            Log.d("PREZZO TOTALE", String.valueOf(totale));
        }



        lvFarmaci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                farmaco f = (farmaco) lvFarmaci.getItemAtPosition(position);
                int idFarmaco = f.id;
                double prezzo = f.getPrezzo();
                Intent i = new Intent(getApplicationContext(), infoFarmaco.class);
                i.putExtra("id", idFarmaco);
                i.putExtra("nome", f.getNomeFarmaco().toString());
                i.putExtra("tipoFarmaco", f.getTipoFarmaco());
                i.putExtra("prezzo", prezzo);
                i.putExtra("idFarmacia", f.getIdFarmacia());
                i.putExtra("username",username);
                a++;
                i.putExtra("a", a);
                startActivity(i);



            }
        });

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), listaFarmacia.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        bt_continua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<farmaco> elenco = riepilogoOrdine.getElenco();
                if(elenco.size()==0){
                    Context context = getApplicationContext();
                    CharSequence text = "Devi aggiungere almeno un farmaco!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), compraFarmaci.class);
                    intent.putExtra("username", username);
                    intent.putExtra("idFarmacia", idFarmacia);
                    Log.d("TOTALE INTENT", String.valueOf(totale));
                    intent.putExtra("tipologia", tipologia);
                    startActivity(intent);
                }
            }

        });
    }

    public void riempiListView(ListView lv) {

    Cursor c;
    c = db.queryFarmaco(idFarmacia);
    List<farmaco> arrayList = new ArrayList<farmaco>();
    while (c.moveToNext()) {
        farmaco f = new farmaco(c.getInt(0),c.getString(1), c.getInt(3), c.getInt(2), c.getDouble(4),c.getInt(5));
        if(c.getInt(3)== 0) {
        }
        arrayList.add(f);
    }

    ArrayAdapter<farmaco> ad = new ArrayAdapter<farmaco>(
            this, android.R.layout.simple_list_item_1, arrayList);
    lv.setAdapter(ad);

    }

    @Override
    public void forceNotifyDataSetChanged() {
        runOnUiThread(new Runnable() {
            public void run() {
                customAdapter.notifyDataSetChanged();
            }
        });
    }
}
