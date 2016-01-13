package com.example.vale.farmacia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import database.dataBaseManager;

/**
 * Created by Vale on 18/11/2015.
 */
public class infoFarmacia extends Activity implements OnMapReadyCallback {
    dataBaseManager db = new dataBaseManager(this);
    int idFarmacia;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_farmacia);
        addListenerOnRatingBar();
        addListenerOnButton();

        Intent intent = getIntent();
        idFarmacia = intent.getIntExtra("id", -1);
        username = intent.getStringExtra("username");
        final TextView txt_testochisiamo = (TextView) this.findViewById(R.id.txt_testochisiamo);
        final TextView txt_testotelefono = (TextView) this.findViewById(R.id.txt_testotelefono);
        final TextView txt_testoindirizzo = (TextView) this.findViewById(R.id.txt_testoindirizzo);
        final Button bt_sezionecomprafarmaco = (Button) this.findViewById(R.id.bt_sezionecomprafarmaco);
        final TextView txtVotoMedia = (TextView) this.findViewById(R.id.txtVotoMedia);

        //array informazioni
        String[] info;
        // richiamo metodo riempiTextView e salvo il risultato nell'array
        info = db.riempiTextView(idFarmacia);
        txt_testochisiamo.setText(info[0].toString());
        txt_testotelefono.setText(info[1].toString());
        txt_testoindirizzo.setText(info[2].toString());
        String media = "Valutazione media farmacia: " + String.format("%.2f",db.valutazioneMedia(idFarmacia));
        txtVotoMedia.setText(media);


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

        bt_sezionecomprafarmaco.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), listaFarmaci.class);
                intent.putExtra("username", username);
                intent.putExtra("idFarmacia", idFarmacia);
                startActivity(intent);
            }

        });

    }


    @Override
    public void onMapReady(GoogleMap map) {
        double latitudine = db.getLat(idFarmacia);
        double longitudine = db.getLong(idFarmacia);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitudine, longitudine), 16)); // Coordinate del punto in cui si centra la mappa inizialmente

        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.

        //Serve per posizionare un marker rosso sulla mappa
        map.addMarker(new MarkerOptions()
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(latitudine, longitudine)));
    }

    public void addListenerOnRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.rb_voto);
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,boolean fromUser) {
            }
        });
    }

    public void addListenerOnButton() {
        ratingBar = (RatingBar) findViewById(R.id.rb_voto);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    //prove stampa
                    //Log.d("RISULTATO", String.valueOf(idFarmacia));
                    //Log.d("RISULTATO", username);
                    //Log.d("RISULTATO", String.valueOf(ratingBar.getRating()));
                //verifica se l'utente ha gia inserito o meno una recensione in quella farmacia
                //se l'untente non ha inserito nessun voto --> inserisce
                if(db.verificaEsistenzaUsernameRecensione(username,idFarmacia)== false){
                    db.salvaValutazione(idFarmacia,username,String.valueOf(ratingBar.getRating()));
                    Context context = getApplicationContext();
                    CharSequence text = "Grazie, il tuo voto e stato inviato";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), infoFarmacia.class);
                    intent.putExtra("id", idFarmacia);
                    intent.putExtra("username", username);
                    startActivity(intent);


                    //altrimenti se è gia stato inserita una recensione(voto)
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Hai gia valutato questa farmacia!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

        });

    }

}

