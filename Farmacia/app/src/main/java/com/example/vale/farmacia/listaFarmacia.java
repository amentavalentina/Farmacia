package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import database.dataBaseManager;
import database.dataBase_string;

/**
 * Created by Vale on 16/11/2015.
 */
public class listaFarmacia extends Activity {
    dataBaseManager db = new dataBaseManager(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vale.farmacia.R.layout.lista_farmacia);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        final ListView lvFarmacia = (ListView) this.findViewById(com.example.vale.farmacia.R.id.lvFarmacia);
        riempiListView(lvFarmacia);

        //gestione OnItemClick sulla listView
        lvFarmacia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                farmacia f = (farmacia) lvFarmacia.getItemAtPosition(position);
                int idFarmacia = f.id;
                Intent i = new Intent(getApplicationContext(), infoFarmacia.class);
                i.putExtra("id", idFarmacia);
                i.putExtra("username",username);
                List<farmaco> elenco = riepilogoOrdine.getElenco();
                elenco.clear();
                riepilogoOrdine.setElenco(elenco);
                startActivity(i);


            }
        });
    }

    public void riempiListView(ListView lv) {

        Cursor c;
        c = db.query(dataBase_string.TBL_FARMACIA);
        List<farmacia> arrayList = new ArrayList<farmacia>();
        while (c.moveToNext()) {
            farmacia f = new farmacia(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
            arrayList.add(f);
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<farmacia> ad = new ArrayAdapter<farmacia>(
                this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(ad);

    }
}


