package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import database.dataBaseManager;

/**
 * Created by Vale on 06/12/2015.
 */
public class infoFarmaco extends Activity{
    int idFarmaco;
    private String nomeFarmaco;
    dataBaseManager db = new dataBaseManager(this);
    Button bt_compra;
    TextView tv_farmacoNonDisponibile;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_farmaco);
        final TextView txt_testoNomeFarmaco = (TextView) this.findViewById(R.id.txt_testoNomeFarmaco);
        final TextView txt_testoPrezzoFarmaco = (TextView) this.findViewById(R.id.txt_testoPrezzoFarmaco);
        bt_compra = (Button) this.findViewById(R.id.bt_compra);
        tv_farmacoNonDisponibile = (TextView) this.findViewById(R.id.tv_farmacoNonDisponibile);

        //dall'activity precedente mi passa tutto quello che mi serve nella nuova activity
        Intent intent = getIntent();
        idFarmaco = intent.getIntExtra("id", -1);
        final String nomeFarmaco = intent.getStringExtra("nome");
        final double prezzo = intent.getDoubleExtra("prezzo", 0.0);
        final int tipoFarmaco = intent.getIntExtra("tipoFarmaco", -1);
        final int idFarmacia = intent.getIntExtra("idFarmacia", -1);
        final int a = intent.getIntExtra("a",-1);
        final String username = intent.getStringExtra("username");

        txt_testoNomeFarmaco.setText(nomeFarmaco);
        txt_testoPrezzoFarmaco.setText(String.valueOf(prezzo) + " euro ");


        final Spinner sp_quantita = (Spinner) this.findViewById(R.id.sp_quantita);
        //riempo lo spinner
        riempiSpinner(sp_quantita, idFarmaco);


        //gestione OnClick sul bottone compra
        bt_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantita =  Integer.parseInt(sp_quantita.getSelectedItem().toString());
                double costoFarmaci = prezzo*quantita;
               // farmaco f = new farmaco(idFarmaco,nomeFarmaco,quantita,tipoFarmaco,costoFarmaci,idFarmacia);
                Intent i = new Intent(getApplicationContext(), listaFarmaci.class);
                // oggetti passati nell'intent
                i.putExtra("idFarmaco",idFarmaco);
                i.putExtra("nomeFarmaco", nomeFarmaco);
                i.putExtra("quantita", quantita);
                i.putExtra("tipoFarmaco", tipoFarmaco);
                i.putExtra("costoFarmaci", costoFarmaci);
                i.putExtra("idFarmacia", idFarmacia);
                i.putExtra("accesso", 1);
                i.putExtra("a", a);
                i.putExtra("username",username);
                startActivity(i);

            }

            });

    }

    //metodo che riempe lo spinner della quantita del farmaco
    public void riempiSpinner(Spinner spinner,int idFarmaco){

        ArrayAdapter ad = new ArrayAdapter<farmaco>(this,android.R.layout.simple_dropdown_item_1line);
        Cursor c;
        spinner.setAdapter(ad);
        //query sul db che restituisce la quantita del farmaco (idFarmaco)
        c = db.quantitaFarmaco(idFarmaco);
        try{
            while(c.moveToNext()){
                int quantita = c.getInt(0);
                if (quantita == 0){
                    bt_compra.setEnabled(false);
                    tv_farmacoNonDisponibile.setText("Farmaco momentaneamente non disponibile!");
                    tv_farmacoNonDisponibile.setTextColor(Color.RED);


                }
                //Log.d("QUANTITA",String.valueOf(quantita));
                for (int i = 1; i<=quantita ; i++){
                    ad.add(i);
                }
            }
        }
        finally {
            c.close();
        }
    }

}

