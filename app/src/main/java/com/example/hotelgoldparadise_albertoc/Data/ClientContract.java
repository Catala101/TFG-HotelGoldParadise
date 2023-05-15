package com.example.hotelgoldparadise_albertoc.Data;
 import android.provider.BaseColumns;

public class ClientContract {

    private ClientContract(){

    }

    public static final class ClientEntry implements BaseColumns{

        public final static String TABLE_NAME = "Clientes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CLIENT_NAME = "nombre";
        public final static String COLUMN_CLIENT_DNI = "dni";
        public final static String COLUMN_CLIENT_RESIDENTS = "num_residentes";
        public final static String COLUMN_CLIENT_DAYS = "num_dias";
        public final static String COLUMN_CLIENT_ROOM = "habitacion";
        public final static String COLUMN_CLIENT_N_ROOM = "num_habitacion";
        public final static String COLUMN_CLIENT_PRICE = "precio";
    }
}
