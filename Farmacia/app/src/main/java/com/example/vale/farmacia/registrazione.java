package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import database.dataBaseManager;

public class registrazione extends Activity {
    dataBaseManager db = new dataBaseManager(this);
    int numerotessera;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vale.farmacia.R.layout.registrazione);


        final EditText txtNome = (EditText) this.findViewById(R.id.txtNome);
        final EditText txtCognome = (EditText) this.findViewById(R.id.txtCognome);
        final EditText txtUsername = (EditText) this.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) this.findViewById(R.id.txtPassword);
        final EditText txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        final EditText txtIndirizzo = (EditText) this.findViewById(R.id.txtIndirizzo);
        final EditText txtCodiceFiscale = (EditText) this.findViewById(R.id.txtCodiceFiscale);
        final EditText txtNumeroTessera = (EditText) this.findViewById(R.id.txtNumeroTessera);
        final Button btRegistrati = (Button) this.findViewById(R.id.btRegistrati);


        /*controlli riempimento text e button */
        btRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //controlla che tutti i campi siano pieni
                if (txtNome.getText().toString().isEmpty() == false && txtCognome.getText().toString().isEmpty() == false &&
                        txtUsername.getText().toString().isEmpty() == false && txtPassword.getText().toString().isEmpty() == false &&
                        txtEmail.getText().toString().isEmpty() == false && txtIndirizzo.getText().toString().isEmpty() == false &&
                        txtCodiceFiscale.getText().toString().isEmpty() == false) {
                    //controlla il formato della mail
                    if (function.isValidEmail(txtEmail) == true) {
                        //controlla che lo username non sia gi esistente
                        if (db.existUsername(txtUsername.getText().toString()) == false) {

                            if (txtNumeroTessera.getText().toString().isEmpty() == true) {
                                Log.d("SONO VUOTO", txtNumeroTessera.getText().toString());
                                numerotessera = -1;
                            } else if (txtNumeroTessera.getText().toString().isEmpty() == false) {
                                Log.d("SONO PIENO", txtNumeroTessera.getText().toString());
                                if (txtNumeroTessera.getText().toString().length() >= 10) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Numero tessera troppo lungo";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                } else {
                                    numerotessera = Integer.parseInt(txtNumeroTessera.getText().toString());
                                }
                            }
                                    db.salvaAccount(txtNome.getText().toString(), txtCognome.getText().toString(), txtUsername.getText().toString(),
                                            function.md5(txtPassword.getText().toString()), txtEmail.getText().toString(), txtIndirizzo.getText().toString(),
                                            txtCodiceFiscale.getText().toString(), numerotessera);
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    Context context = getApplicationContext();
                                    CharSequence text = "Registrazione effettuato con successo!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    startActivity(i);

                            } else {
                                Context context = getApplicationContext();
                                CharSequence text = "Username gia esistente";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

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
            }

            );

            CheckBox cbShowPassword = (CheckBox) this.findViewById(R.id.cbShowPassword);
            cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                                      {
    @Override
     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
     if (isChecked) {
     txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
     } else {txtPassword.setInputType(129);
                }
            }
      }

            );
        }
    }



