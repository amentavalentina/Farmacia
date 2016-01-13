package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vale.farmacia.Voto;
import com.example.vale.farmacia.farmacia;
import com.example.vale.farmacia.farmaco;
import com.example.vale.farmacia.tipoFarmaco;

/**
 * Pagina dedicata alla creazione delle varie tabelle utilizzate all'interno del DATABASE
 */
public class dataBase extends SQLiteOpenHelper {

    private dataBase database;

    /*Costruttore*/
  public dataBase(Context context) { super (context, dataBase_string.DBNAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {

/* Creo la tabella per gli UTENTI*/
        String tabellautente = "CREATE TABLE "+ dataBase_string.TBL_UTENTE +
                "( " + dataBase_string.U_FIELD_NOMEUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_COGNOMEUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_USERNAMEUTENTE+ " TEXT PRIMARY KEY,"+
                dataBase_string.U_FIELD_PASSWORDUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_EMAILUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_INDIRIZZOUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_CODICEFISCALEUTENTE+ " TEXT not null,"+
                dataBase_string.U_FIELD_NUMEROTESSERAUTENTE+ " INTEGER);";

        db.execSQL(tabellautente);

/* Creo la tabella: FARMACO*/

        String tabellafarmaco = "CREATE TABLE "+ dataBase_string.TBL_FARMACO +
                "( " + dataBase_string.F_FIELD_IDFARMACO+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                dataBase_string.F_FIELD_NOMEFARMACO+ " TEXT not null,"+
                dataBase_string.F_FIELD_TIPOFARMACO+ " INTEGER not null,"+
                dataBase_string.F_FIELD_QUANTITAFARMACO+ " INTEGER not null,"+
                dataBase_string.F_FIELD_PREZZO+ " DOUBLE not null,"+
                dataBase_string.F_FIELD_IDFARMACIA+ " INTEGER not null,"+
                " FOREIGN KEY (" + dataBase_string.F_FIELD_IDFARMACIA + ") REFERENCES " + dataBase_string.TBL_FARMACIA + " (" + dataBase_string.FA_FIELD_NOMEFARMACIA + "),"+
                " FOREIGN KEY (" + dataBase_string.F_FIELD_TIPOFARMACO + ") REFERENCES " + dataBase_string.TBL_TIPOLOGIAFARMACO + " (" + dataBase_string.TF_FIELD_IDTIPOLOGIAFARMACO + "));";

        db.execSQL(tabellafarmaco);


 /* Creo la tabella : TIPOLOGIA FARMACO*/

        String tabellatipologiafarmaco = "CREATE TABLE "+ dataBase_string.TBL_TIPOLOGIAFARMACO +
                "( " + dataBase_string.TF_FIELD_IDTIPOLOGIAFARMACO+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                dataBase_string.TF_FIELD_OBBLIGORICETTA+ " TEXT not null,"+
                dataBase_string.TF_FIELD_DESCRIZIONETIPOLOGIAFARMACO+ " INTEGER not null);";

        db.execSQL(tabellatipologiafarmaco);



 /* Creo la tabella: FARMACIA*/

        String tabellafarmacia = "CREATE TABLE "+ dataBase_string.TBL_FARMACIA +
                "( " + dataBase_string.FA_FIELD_IDFARMACIA+ " INTEGER PRIMARY KEY,"+
                dataBase_string.FA_FIELD_NOMEFARMACIA+ " TEXT not null,"+
                dataBase_string.FA_FIELD_INDIRIZZOFARMACIA+ " TEXT not null,"+
                dataBase_string.FA_FIELD_CHISIAMOFARMACIA+ " TEXT not null,"+
                dataBase_string.FA_FIELD_TELEFONOFARMACIA+ " TEXT not null,"+
                dataBase_string.FA_FIELD_LATITUDINEFARMACIA+ " TEXT not null,"+
                dataBase_string.FA_FIELD_LONGITUDINEFARMACIA+ " TEXT not null);";

        db.execSQL(tabellafarmacia);



 /* Creo la tabella: RECENSIONE*/

        String tabellarecensione = "CREATE TABLE "+ dataBase_string.TBL_RECENSIONE +
                "( " + dataBase_string.R_FIELD_IDRECENSIONE+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                dataBase_string.R_FIELD_IDFARMACIA+ " INTEGER not null,"+
                dataBase_string.R_FIELD_VALUTAZIONE+ " INTEGER not null,"+
                dataBase_string.R_FIELD_USERNAME+ " TEXT not null,"+
                " FOREIGN KEY (" + dataBase_string.R_FIELD_USERNAME + ") REFERENCES " + dataBase_string.TBL_UTENTE + " (" + dataBase_string.U_FIELD_USERNAMEUTENTE + "),"+
                " FOREIGN KEY (" + dataBase_string.R_FIELD_IDFARMACIA + ") REFERENCES " + dataBase_string.TBL_FARMACIA + " (" + dataBase_string.FA_FIELD_IDFARMACIA + "));";

                db.execSQL(tabellarecensione);


        popolaDbFarmacie(db);
        popolaDbTipiFarmaci(db);
        popolaDbFarmaco(db);

    }

    private void popolaDbFarmacie(SQLiteDatabase db) {
        int size = 2;
        farmacia[] f = new farmacia[size];
        f[0]=new farmacia (0,"Farmacia Comunale Croce","Via Crocioni Giovanni, 1, 40133 Bologna BO","La Farmacia Croce del dottor Alessandro Rossi da oltre dieci anni offre i migliori prodotti a Bologna e dal 2007 e' presente anche online sia su ebay che con il proprio sito. ","051 435958","44.492645","11.301243");
        f[1]=new farmacia(1,"Farmacia Comunale Casalecchio","Via Porrettana 450, 1, 40033 Casalecchio di Reno BO","La Farmacia Casalecchio del dottor Alessandro Rossi da oltre dieci anni offre i migliori prodotti a Bologna e dal 2007 e' presente anche online sia su ebay che con il proprio sito. ","051 435958","44.474447", "11.272428");

        for (int i = 0; i<f.length; i++){
            ContentValues cv = new ContentValues();
            cv.put(dataBase_string.FA_FIELD_IDFARMACIA, f[i].getId());
            cv.put(dataBase_string.FA_FIELD_NOMEFARMACIA, f[i].getNome());
            cv.put(dataBase_string.FA_FIELD_INDIRIZZOFARMACIA, f[i].getIndirizzo());
            cv.put(dataBase_string.FA_FIELD_CHISIAMOFARMACIA, f[i].getChisiamo());
            cv.put(dataBase_string.FA_FIELD_TELEFONOFARMACIA, f[i].getTelefono());
            cv.put(dataBase_string.FA_FIELD_LATITUDINEFARMACIA, f[i].getLatitutdine());
            cv.put(dataBase_string.FA_FIELD_LONGITUDINEFARMACIA, f[i].getLongitudine());
            try{
                //inserisco i valori all'interno del database
                db.insert(dataBase_string.TBL_FARMACIA,null,cv);
            }catch (SQLiteException sqle){
                sqle.printStackTrace();
            }
        }


    }

    private void popolaDbTipiFarmaci(SQLiteDatabase db) {

        int size = 3;
        tipoFarmaco[] tf = new tipoFarmaco[size];
        tf[0] = new tipoFarmaco(0, "fascia A", "si");
        tf[1] = new tipoFarmaco(1, "fascia C", "si");
        tf[2] = new tipoFarmaco(2, "fascia C-bis", "no");

        for (int i = 0; i < tf.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put(dataBase_string.TF_FIELD_IDTIPOLOGIAFARMACO, tf[i].getId());
            cv.put(dataBase_string.TF_FIELD_DESCRIZIONETIPOLOGIAFARMACO, tf[i].getDescrizione());
            cv.put(dataBase_string.TF_FIELD_OBBLIGORICETTA, tf[i].getObbligoricetta());
            try {
                db.insert(dataBase_string.TBL_TIPOLOGIAFARMACO, null, cv);
            } catch (SQLiteException sqle) {
                sqle.printStackTrace();
            }
        }
    }
    private void popolaDbFarmaco(SQLiteDatabase db) {

        int size = 19;
        farmaco[] f = new farmaco[size];

        //nome prodotto, quantità, tipologia, prezzo
        // FARMACI FASCIA A ----> 0
        f[0] = new farmaco("OMEPRAZOLO 14cps gastrores 10mg", 10, 0, 3.22,0);
        f[1] = new farmaco("MEPRAL 14cps gastrores 20mg", 20, 0, 8.44,1);
        f[2] = new farmaco("PANTOPRAZOLO 14cpr gastrores 20mg", 5, 0, 4.31,1);
        f[3] = new farmaco("TORADOL 3 fiale IM EV 30mg 1ml", 8, 0, 4.06,1);
        f[4] = new farmaco("CONTRAMAL OS GTT 10ML 100mg/ml", 5, 0, 7.0,1);
        f[5] = new farmaco("ONCOCARBIDE 20cps 500mg", 50, 0, 8.91,0);
        f[6] = new farmaco("AIRCORT SOSPxINAL 200D 200mcg", 30, 0, 27.95,0);
        f[7] = new farmaco("ZIMOX 1g 12cps", 30, 0, 6.95,0);
        // FARMACI FASCIA C  ----> 1
        f[8] = new farmaco("ZIMOX 10g/2000ml gocce sosp 1 flacone 20ml + 1 fiala solvente 16ml", 10, 1, 6.35,0);
        f[9] = new farmaco("FLUIMUCIL 20cpr eff 600mg ", 4, 1, 8.25,1);
        f[10] = new farmaco("ASPIRINETTA 30cpr 100mg", 10, 1, 4.10,1);
        f[11] = new farmaco("GENTAMICINA E BECLOMETASON crema derm 30g ", 15, 1, 7.45,0);
        f[12] = new farmaco("MENADERM 0,025% crema 30g ", 5, 1, 8.00,0);

        // FARMACI FASCIA C-bis  ----> 2
       f[13] = new farmaco("TACHIPIRINA 20cps 5000mg", 30, 2, 4.80,0);
       f[14] = new farmaco("ENTEROGERMINA 2 MILIARDI 20 flaconcini  5ml", 50, 2, 7.00,1);
       f[15] = new farmaco("ASPIRINA 10 cps 400mg COMPRESSE EFFERVESCENTI CON VITAMINA C", 20, 2, 3.85,1);
       f[16] = new farmaco("FLUIBRON 15mg/2ml SOLUZIONE DA NEBULIZZARE 20 CONTENITORI MONODOSE 2ml", 18, 2, 11.55,1);
       f[17] = new farmaco("TACHIPIRINA 12cps 5000mg", 30, 2, 2.80,0);
       f[18] = new farmaco("BENAGOL 12cpr gusto arancia", 30, 2, 2.80,1);

        for (int i = 0; i < f.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put(dataBase_string.F_FIELD_NOMEFARMACO, f[i].getNomeFarmaco());
            cv.put(dataBase_string.F_FIELD_QUANTITAFARMACO, f[i].getQuantitaFarmaco());
            cv.put(dataBase_string.F_FIELD_TIPOFARMACO, f[i].getTipoFarmaco());
            cv.put(dataBase_string.F_FIELD_PREZZO, f[i].getPrezzo());
            cv.put(dataBase_string.F_FIELD_IDFARMACIA, f[i].getIdFarmacia());
            Log.d("PREZZO", String.valueOf(f[i].getPrezzo()));
            try {
                db.insert(dataBase_string.TBL_FARMACO, null, cv);
            } catch (SQLiteException sqle) {
                sqle.printStackTrace();
            }
        }
    }

/*
    private void popolaValutazione(SQLiteDatabase db){

        Voto[] voto = new Voto[5];
        voto[0] = new Voto(0, "Nessuna Valutazione");
        voto[1] = new Voto(1, "Pessimo Servizio");
        voto[2] = new Voto(2, "Discreto Servizio");
        voto[3] = new Voto(3, "Buon Servizio");
        voto[4] = new Voto(4, "Ottimo Serivizio");
        for (int i = 0; i < voto.length; i++) {
            //Riempo la tabella
            ContentValues cv = new ContentValues();
            cv.put(dataBase_string.V_FIELD_IDVOTO, voto[i].getId());
            cv.put(dataBase_string.V_FIELD_TESTOVOTO, voto[i].getVoto());
            try{
                //inserisco i valori all'interno del database
                db.insert(dataBase_string.TBL_VOTO, null, cv);
            }catch (SQLiteException sqle){
                sqle.printStackTrace();
            }
    }}*/
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
