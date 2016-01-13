package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.vale.farmacia.farmacia;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Pagina dedicata per la creazione di metodi/query utilizzati dal DATABASE
 */
public class dataBaseManager {
    private dataBase database;

    public dataBaseManager(Context ctx) {
        database = new dataBase(ctx);
    }

    public void salvaAccount(String nomeutente, String cognomeutente, String usernameutente, String passwordutente, String emailutente, String indirizzoutente,
                             String codicefiscaleutente, int numerotesserautente) {

        SQLiteDatabase db = database.getWritableDatabase();
        /*Riempo la tabella*/
        ContentValues cv = new ContentValues();
        cv.put(dataBase_string.U_FIELD_NOMEUTENTE, nomeutente);
        cv.put(dataBase_string.U_FIELD_COGNOMEUTENTE, cognomeutente);
        cv.put(dataBase_string.U_FIELD_USERNAMEUTENTE, usernameutente);
        cv.put(dataBase_string.U_FIELD_PASSWORDUTENTE, passwordutente);
        cv.put(dataBase_string.U_FIELD_EMAILUTENTE, emailutente);
        cv.put(dataBase_string.U_FIELD_INDIRIZZOUTENTE, indirizzoutente);
        cv.put(dataBase_string.U_FIELD_CODICEFISCALEUTENTE, codicefiscaleutente);
        cv.put(dataBase_string.U_FIELD_NUMEROTESSERAUTENTE, numerotesserautente);

        try {
            db.insert(dataBase_string.TBL_UTENTE, null, cv);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }

    }

    /*metodo login
    * restituisce 0 se il cursore  vuoto (la query non da risultati)
    * restituisce 1 se il cursore  piento (la query da risultati)
    */
    public int login(String username, String password) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT * FROM " + dataBase_string.TBL_UTENTE + " WHERE " + dataBase_string.U_FIELD_USERNAMEUTENTE
                    + " = '" + username + "'" + " AND " + dataBase_string.U_FIELD_PASSWORDUTENTE + " = '" + password + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            if (c.moveToNext() == false) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLiteException sqle) {
            return -1;
        }
    }

    //metodo che verifica se un username  gia esistente
    public boolean existUsername(String username) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT * FROM " + dataBase_string.TBL_UTENTE + " WHERE " + dataBase_string.U_FIELD_USERNAMEUTENTE
                    + " = '" + username + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            if (c.moveToNext() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLiteException sqle) {
            return false;
        }
    }

    public double getLat(int id) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT " + dataBase_string.FA_FIELD_LATITUDINEFARMACIA + " FROM " + dataBase_string.TBL_FARMACIA + " WHERE " + dataBase_string.FA_FIELD_IDFARMACIA + " = '" + id + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            c.moveToNext();
            double latitudine = c.getDouble(0);
            //prova stampa cordinate
            //Log.d("AAAAAAAAAAAAAAA", String.valueOf(latitudine));Log.d("AAAAAAAAAAAAAAA", String.valueOf(latitudine));Log.d("AAAAAAAAAAAAAAA", String.valueOf(latitudine));Log.d("AAAAAAAAAAAAAAA", String.valueOf(latitudine));
            return latitudine;
        } catch (SQLiteException sqle) {
            return 0;
        }
    }


    public double getLong(int id) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT " + dataBase_string.FA_FIELD_LONGITUDINEFARMACIA + " FROM " + dataBase_string.TBL_FARMACIA + " WHERE " + dataBase_string.FA_FIELD_IDFARMACIA + " = '" + id + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            c.moveToNext();
            double longitudine = c.getDouble(0);
            return longitudine;
        } catch (SQLiteException sqle) {
            return 0;
        }
    }

    public Cursor query(String tbl_name) {
        Cursor crs = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            crs = db.query(tbl_name, null, null, null, null, null, null, null);
        } catch (SQLiteException sqle) {
            return null;
        }
        return crs;
    }

    public Cursor queryFarmaco(int idFarmacia) {
        Cursor crs = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            String q = "SELECT * FROM " + dataBase_string.TBL_FARMACO + " WHERE " +
                    dataBase_string.F_FIELD_IDFARMACIA + " = '" + idFarmacia + "' ORDER BY " + dataBase_string.F_FIELD_NOMEFARMACO;
            Log.d("QUERY", q);
            crs = db.rawQuery(q, null);
        } catch (SQLiteException sqle) {
            return null;
        }
        return crs;
    }

    public String[] riempiTextView(int idFarmacia) {
        Cursor c = null;
        String[] info = new String[3];
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT " + dataBase_string.FA_FIELD_CHISIAMOFARMACIA + ", " +
                    dataBase_string.FA_FIELD_TELEFONOFARMACIA + ", " +
                    dataBase_string.FA_FIELD_INDIRIZZOFARMACIA +
                    " FROM " + dataBase_string.TBL_FARMACIA + " WHERE " +
                    dataBase_string.FA_FIELD_IDFARMACIA + " = '" + idFarmacia + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            c.moveToNext();
            for (int i = 0; i < info.length; i++) {
                info[i] = c.getString(i);
                Log.d("RISULTATO", c.getString(i));
            }
        } catch (SQLiteException sqle) {
            return null;
        }
        return info;
    }

    public void salvaValutazione(int idFarmacia, String username, String voto) {
        SQLiteDatabase db = database.getWritableDatabase();
        /*Riempo la tabella*/
        ContentValues cv = new ContentValues();
        cv.put(dataBase_string.R_FIELD_IDFARMACIA, idFarmacia);
        cv.put(dataBase_string.R_FIELD_USERNAME, username);
        cv.put(dataBase_string.R_FIELD_VALUTAZIONE, voto);
        try {
            db.insert(dataBase_string.TBL_RECENSIONE, null, cv);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }


    }

    public Boolean verificaEsistenzaUsernameRecensione(String username, int idFarmacia) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            String q = "SELECT * FROM " + dataBase_string.TBL_RECENSIONE + " WHERE " + dataBase_string.R_FIELD_USERNAME
                    + " = '" + username + "' AND " + dataBase_string.R_FIELD_IDFARMACIA + " = '" + idFarmacia + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
            if (c.moveToNext() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLiteException sqle) {
            return false;
        }
    }

    public Cursor quantitaFarmaco(int idFarmaco) {
        Cursor crs = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            String q = "SELECT " + dataBase_string.F_FIELD_QUANTITAFARMACO + " FROM " + dataBase_string.TBL_FARMACO + " WHERE " +
                    dataBase_string.F_FIELD_IDFARMACO + " = '" + idFarmaco + "'";
            Log.d("QUERY", q);
            crs = db.rawQuery(q, null);
        } catch (SQLiteException sqle) {
            return null;
        }
        return crs;
    }

    public void updateQuantita(int id, int quantitaFarmaco) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //richiamo il metodo che mi restituisce la quantita del farmaco in base all'id
        Cursor c = quantitaFarmaco(id);
        //la nuova quantita sara : vecchia quantita - quantità passata come parametro
       // Log.d("PARAMETRO QUANTITA ", String.valueOf(quantitaFarmaco));
        c.moveToNext();
        int oldQuantita = c.getInt(0);
        //Log.d("OLD QUANTITA ", String.valueOf(oldQuantita));
        int newQuantita = oldQuantita - quantitaFarmaco;
       // Log.d("NEW QUANTITA ", String.valueOf(newQuantita));
        cv.put(dataBase_string.F_FIELD_QUANTITAFARMACO, newQuantita);
        String where = "idfamrmaco" + " = '" + id + "'";
        try {
            db.update(dataBase_string.TBL_FARMACO, cv, where, null);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }


    }

    public double valutazioneMedia(int idFarmacia) {
        Cursor crs = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            String q = "SELECT AVG(" + dataBase_string.R_FIELD_VALUTAZIONE + ") FROM " + dataBase_string.TBL_RECENSIONE + " WHERE " +
                    dataBase_string.R_FIELD_IDFARMACIA + " = '" + idFarmacia + "'";
            Log.d("QUERY", q);
            crs = db.rawQuery(q, null);
            crs.moveToNext();
            double media = crs.getDouble(0);
            return media;
        } catch (SQLiteException sqle) {
            return -1;
        }

    }

    public int getNumeroTessera(String username) {
        Cursor crs = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            String q = "SELECT " + dataBase_string.U_FIELD_NUMEROTESSERAUTENTE + " FROM " + dataBase_string.TBL_UTENTE + " WHERE " +
                    dataBase_string.U_FIELD_USERNAMEUTENTE + " = '" + username + "'";
            Log.d("QUERY", q);
            crs = db.rawQuery(q, null);
            crs.moveToNext();
            int numeroTessera = crs.getInt(0);
            return numeroTessera;
        } catch (SQLiteException sqle) {
            return -1;
        }
    }

    public Cursor recuperaAccount(String username) {
        Cursor c = null;
        try {
            SQLiteDatabase db = database.getReadableDatabase();
            String q = "SELECT * FROM " + dataBase_string.TBL_UTENTE + " WHERE " + dataBase_string.U_FIELD_USERNAMEUTENTE + " = '" + username + "'";
            Log.d("QUERY", q);
            c = db.rawQuery(q, null);
        } catch (SQLiteException sqle) {
            return null;
        }
        return c;
    }

    public void updateAccount(String username, String email, String indirizzo, int numeroTessera) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dataBase_string.U_FIELD_EMAILUTENTE, email);
        cv.put(dataBase_string.U_FIELD_INDIRIZZOUTENTE, indirizzo);
        cv.put(dataBase_string.U_FIELD_NUMEROTESSERAUTENTE, numeroTessera);
        String where = "usernameutente" + " = '" + username + "'";
        try {
            db.update(dataBase_string.TBL_UTENTE, cv, where, null);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }
    }
}

