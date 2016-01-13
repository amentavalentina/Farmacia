package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import database.dataBaseManager;


public class MainActivity extends Activity {
    private dataBaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vale.farmacia.R.layout.activity_main);
        db = new dataBaseManager(MainActivity.this);

        //dichiarazione degli oggetti creati nella  view
        final EditText txtUsername = (EditText) this.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) this.findViewById(com.example.vale.farmacia.R.id.txtPassword);
        final Button btLogin = (Button) this.findViewById(com.example.vale.farmacia.R.id.btLogin);
        final Button btRegistrati = (Button) this.findViewById(com.example.vale.farmacia.R.id.btRegistrati);
        final Button btNext = (Button) this.findViewById(R.id.btNext);
        final Button btImpostazioniAccount = (Button) this.findViewById(R.id.btImpostazioniAccount);
        final  TextView tv_login_2 = (TextView) this.findViewById(R.id.tv_login_2);
        final LinearLayout l1 = (LinearLayout) this.findViewById(R.id.configuration_main);
        final LinearLayout l2 = (LinearLayout) this.findViewById(R.id.configuration_main_2);
        l2.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        int a = i.getIntExtra("collegamento", -1);
        final String username = i.getStringExtra("username");
        if(a == 1000){
            l1.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.VISIBLE);
            tv_login_2.setText("Benvenuto " + username);
            //gestione onClick sul bottone next
            btImpostazioniAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), modificaUtente.class);
                    i.putExtra("username", username);
                    startActivity(i);
                }
            });
            //gestione onClick sul bottone impostazioni account
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), listaFarmacia.class);
                    i.putExtra("username", username);
                    startActivity(i);
                }
            });


        }
        //gestione onClick sul bottone registrati
        btRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), registrazione.class);
                startActivity(i);
            }
        });

        //gestione onClick sul bottone login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String md5pass = function.md5(txtPassword.getText().toString());
                String username = txtUsername.getText().toString();
                int i = db.login(username, md5pass);
                //errore
                if(txtUsername.getText().toString().isEmpty()== false && txtPassword.getText().toString().isEmpty() == false) {
                    if (i == 0) {
                        Context context = getApplicationContext();
                        CharSequence text = "Username o Password ERRATI";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    //login eseguito
                    if (i == 1) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("collegamento",1000);
                        Context context = getApplicationContext();
                        CharSequence text = "Login effettuato con successo";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        startActivity(intent);
                    }
                }else{
                    //altrimenti non sono stati inseriti tutt i campi
                    Context context = getApplicationContext();
                    CharSequence text = "Inserire tutti i campi";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
                //permette di rendere visibile tramite l apposito clik su checbox la password appena inserita
                CheckBox cbShowPassword = (CheckBox) this.findViewById(com.example.vale.farmacia.R.id.cbShowPassword);
                cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            txtPassword.setInputType(129);
                        }
                    }
                });
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(com.example.vale.farmacia.R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == com.example.vale.farmacia.R.id.action_settings) {
                    Intent i= new Intent(this, about.class);

                    startActivity(i);
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }
        }
