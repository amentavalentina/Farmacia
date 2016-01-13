package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import database.dataBaseManager;

/**
 * Created by Vale on 20/12/2015.
 */
public class compraFarmaci extends Activity {
    dataBaseManager db = new dataBaseManager(this);
    double totale;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compra_farmaci);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        int idFarmacia = intent.getIntExtra("idFarmacia", -1);
        int tipologia = intent.getIntExtra("tipologia",-1);


        RadioGroup rgMetodoPagamento = (RadioGroup) this.findViewById(R.id.rgMetodoPagamento);
        final RadioButton rb_contanti = (RadioButton) this.findViewById(R.id.rb_contanti);
        final RadioButton rb_cartaCredito = (RadioButton) this.findViewById(R.id.rb_cartaCredito);
        final Button bt_acquista = (Button) this.findViewById(R.id.bt_acquista);

        final EditText txt_nomeIntestatario = (EditText) this.findViewById(R.id.txt_nomeIntestatario);
        final EditText txt_numeroCarta = (EditText) this.findViewById(R.id.txt_numeroCarta);
        final EditText txt_meseCarta = (EditText) this.findViewById(R.id.txt_meseCarta);
        final EditText txt_annoCarta = (EditText) this.findViewById(R.id.txt_annoCarta);
        rgMetodoPagamento.check(R.id.rb_contanti);
        bt_acquista.setText("PRENOTA");
        txt_nomeIntestatario.setEnabled(false);
        txt_numeroCarta.setEnabled(false);
        txt_meseCarta.setEnabled(false);
        txt_annoCarta.setEnabled(false);

        TextView tv_allertFarmaco = (TextView) this.findViewById(R.id.tv_allertFarmaco);
        if (tipologia != -1){
            tv_allertFarmaco.setText("Uno o piu' farmaci dell'ordine richiedono la RICETTA per poter essere ritirati!!!");
            tv_allertFarmaco.setTextColor(Color.RED);
        }

        List<farmaco> elenco = riepilogoOrdine.getElenco();
        for (int i = 0; i<elenco.size(); i++) {
            totale = totale + elenco.get(i).getPrezzo();
        }

        TextView tv_totalespesa = (TextView) this.findViewById(R.id.tv_totalespesa);
        tv_totalespesa.setText( String.format("%.2f", totale) );
        TextView tv_scontoTitolo = (TextView) this.findViewById(R.id.tv_scontoTitolo);
        TextView tv_scontoFidaty = (TextView) this.findViewById(R.id.tv_scontoFidaty);
        double totaleScontato = totale-((totale*10)/100);
        tv_scontoFidaty.setText(String.format("%.2f", totaleScontato));
        tv_scontoFidaty.setTextColor(Color.RED);

        if (db.getNumeroTessera(username) == -1){
            tv_scontoTitolo.setVisibility(View.INVISIBLE);
            tv_scontoFidaty.setVisibility(View.INVISIBLE);
        }



        rgMetodoPagamento.check(rb_contanti.getId());
        rgMetodoPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_contanti.isChecked()) {
                    txt_nomeIntestatario.setEnabled(false);
                    txt_numeroCarta.setEnabled(false);
                    txt_meseCarta.setEnabled(false);
                    txt_annoCarta.setEnabled(false);
                    bt_acquista.setText("PRENOTA");
                    bt_acquista.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Context context = getApplicationContext();
                            CharSequence text = "Ti aspettiamo in farmacia per il ritiro, arrivederci e GRAZIE!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            updateFamarci();
                            Intent intent = new Intent(getApplicationContext(), listaFarmacia.class);
                            intent.putExtra("username", username);
                            startActivity(intent);

                        }

                    });
                } else if (rb_cartaCredito.isChecked()) {
                    txt_nomeIntestatario.setEnabled(true);
                    txt_numeroCarta.setEnabled(true);
                    txt_meseCarta.setEnabled(true);
                    txt_annoCarta.setEnabled(true);
                    bt_acquista.setText("ACQUISTA");
                    bt_acquista.setOnClickListener(new View.OnClickListener() {



                        @Override
                        public void onClick(View v) {
                            if (txt_nomeIntestatario.getText().toString().isEmpty() == false && txt_numeroCarta.getText().toString().isEmpty() == false && txt_meseCarta.getText().toString().isEmpty() == false && txt_annoCarta.getText().toString().isEmpty() == false) {

                                //toast
                                updateFamarci();
                                Intent intent = new Intent(getApplicationContext(), listaFarmacia.class);
                                intent.putExtra("username", username);
                                Context context = getApplicationContext();
                                CharSequence text = "Ti aspettiamo in farmacia per il ritiro, arrivederci e GRAZIE!";
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                startActivity(intent);

                            } else {
                                Context context = getApplicationContext();
                                CharSequence text = "Compila tutti i campi";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                        }

                    });
                }

            }
        });
    }



    public void updateFamarci(){
        List<farmaco> elenco = riepilogoOrdine.getElenco();
        for(int i = 0; i <elenco.size(); i++){
            db.updateQuantita(elenco.get(i).getId(), elenco.get(i).getQuantitaFarmaco());
        }
        elenco.clear();
        riepilogoOrdine.setElenco(elenco);
    }




}
