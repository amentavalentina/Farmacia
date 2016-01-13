package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import database.dataBaseManager;

public class modificaUtente extends Activity {
    dataBaseManager db = new dataBaseManager(this);
    String nome ;
    String cognome;
    String password;
    String email;
    String indirizzo;
    String codiceFiscale ;
    String dbUsername;
    int numeroTessera;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vale.farmacia.R.layout.registrazione);

        Intent i = getIntent();
        final String username = i.getStringExtra("username");

        Cursor c = null;
        c = db.recuperaAccount(username);
        while (c.moveToNext()){
            nome = c.getString(0);
            cognome = c.getString(1);
            dbUsername = c.getString(2);
            password = c.getString(3);
            email = c.getString(4);
            indirizzo = c.getString(5);
            codiceFiscale = c.getString(6);
            numeroTessera = c.getInt(7);

        }

        final EditText txtNome = (EditText) this.findViewById(R.id.txtNome);
        final EditText txtCognome = (EditText) this.findViewById(R.id.txtCognome);
        final EditText txtUsername = (EditText) this.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) this.findViewById(R.id.txtPassword);
        final EditText txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        final EditText txtIndirizzo = (EditText) this.findViewById(R.id.txtIndirizzo);
        final EditText txtCodiceFiscale = (EditText) this.findViewById(R.id.txtCodiceFiscale);
        final EditText txtNumeroTessera = (EditText) this.findViewById(R.id.txtNumeroTessera);
        final Button btRegistrati = (Button) this.findViewById(R.id.btRegistrati);
        CheckBox cbShowPassword = (CheckBox) this.findViewById(R.id.cbShowPassword);
        cbShowPassword.setVisibility(View.INVISIBLE);

        txtNome.setText(nome);
        txtNome.setEnabled(false);
        txtCognome.setText(cognome);
        txtCognome.setEnabled(false);
        txtUsername.setText(dbUsername);
        txtUsername.setEnabled(false);
        txtPassword.setText(password);
        txtPassword.setEnabled(false);
        txtEmail.setText(email);
        txtIndirizzo.setText(indirizzo);
        txtCodiceFiscale.setText(codiceFiscale);
        txtCodiceFiscale.setEnabled(false);
        if(numeroTessera == -1){
            txtNumeroTessera.setText("");
        }else {
            txtNumeroTessera.setText(String.valueOf(numeroTessera));
        }
        btRegistrati.setText("SALVA");


        /*controlli riempimento text e button */
        btRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //controlla che tutti i campi siano pieni
                if (txtEmail.getText().toString().isEmpty() == false &&
                        txtIndirizzo.getText().toString().isEmpty() == false ) {
                    //controlla il formato della mail
                    if (function.isValidEmail(txtEmail) == true) {

                            if (txtNumeroTessera.getText().toString().isEmpty() == true) {
                               // Log.d("SONO VUOTO", txtNumeroTessera.getText().toString());
                                numeroTessera = -1;
                            } else if (txtNumeroTessera.getText().toString().isEmpty() == false) {
                                // Log.d("SONO PIENO", txtNumeroTessera.getText().toString());
                                if (txtNumeroTessera.getText().toString().length() >= 10) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Numero tessera troppo lungo";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                } else {
                                    numeroTessera = Integer.parseInt(txtNumeroTessera.getText().toString());
                                }
                            }
                                    db.updateAccount(txtUsername.getText().toString(), txtEmail.getText().toString(), txtIndirizzo.getText().toString(), numeroTessera);
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    i.putExtra("collegamento",1000);
                                    i.putExtra("username",username);
                                    Context context = getApplicationContext();
                                    CharSequence text = "Modifica completata!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    startActivity(i);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Formato email non valido";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

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



