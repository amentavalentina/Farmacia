package database;

/**
 * Pagina dedicata alla costruzione delle stringe utilizzate all'interno del DATABASE.
 */
public class dataBase_string {
    /*--- Nome del data base "FARMACIA" ---*/
    public static final String DBNAME = "farmacia.db";

    /*--- Tabella Utente ---*/
    public static final String TBL_UTENTE="Utente";
    public static final String U_FIELD_NOMEUTENTE ="nomeutente";
    public static final String U_FIELD_COGNOMEUTENTE ="cognomeutente";
    public static final String U_FIELD_USERNAMEUTENTE ="usernameutente";
    public static final String U_FIELD_PASSWORDUTENTE="passwordutente";
    public static final String U_FIELD_EMAILUTENTE ="emailutente";
    public static final String U_FIELD_INDIRIZZOUTENTE ="indirizzoutente";
    public static final String U_FIELD_CODICEFISCALEUTENTE ="codicefiscaleutente";
    public static final String U_FIELD_NUMEROTESSERAUTENTE ="numerotesserautente";

    /*--- Tabella Farmaco ---*/
    public static final String TBL_FARMACO="Farmaco";
    public static final String F_FIELD_IDFARMACO ="idfamrmaco";
    public static final String F_FIELD_NOMEFARMACO ="nomefarmaco";
    public static final String F_FIELD_TIPOFARMACO ="tipofamrmaco";
    public static final String F_FIELD_QUANTITAFARMACO ="quantitafamrmaco";
    public static final String F_FIELD_PREZZO="prezzofarmaco";
    public static final String F_FIELD_IDFARMACIA="idFarmacia";


    /*--- Tabella Tipologia farmaco ---*/
    public static final String TBL_TIPOLOGIAFARMACO="Tipologiafarmaco";
    public static final String TF_FIELD_IDTIPOLOGIAFARMACO ="idtipologiafarmaco";
    public static final String TF_FIELD_OBBLIGORICETTA ="obbligoricetta";
    public static final String TF_FIELD_DESCRIZIONETIPOLOGIAFARMACO ="descrizionetipologiafarmaco";

    /*--- Tabella Farmacia ---*/
    public static final String TBL_FARMACIA="Farmacia";
    public static final String FA_FIELD_IDFARMACIA ="id_farmacia";
    public static final String FA_FIELD_NOMEFARMACIA ="nome_farmacia";
    public static final String FA_FIELD_INDIRIZZOFARMACIA ="indirizzo_farmacia";
    public static final String FA_FIELD_CHISIAMOFARMACIA ="chisiamo_farmacia";
    public static final String FA_FIELD_TELEFONOFARMACIA="telefono_farmacia";
    public static final String FA_FIELD_LATITUDINEFARMACIA ="latitudine_farmacia";
    public static final String FA_FIELD_LONGITUDINEFARMACIA ="longitudine_farmacia";

    /*--- Tabella Recensione ---*/
    public static final String TBL_RECENSIONE="Recensione";
    public static final String R_FIELD_IDRECENSIONE="id_recensione";
    public static final String R_FIELD_IDFARMACIA="id_farmacia";
    public static final String R_FIELD_VALUTAZIONE ="valutazione";
    public static final String R_FIELD_USERNAME="username";

    /*--- Tabella Voto ---*/
    public static final String TBL_VOTO="Voto";
    public static final String V_FIELD_IDVOTO="id_voto";
    public static final String V_FIELD_TESTOVOTO="testo_voto";

}
