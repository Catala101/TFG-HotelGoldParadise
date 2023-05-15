package com.example.hotelgoldparadise_albertoc.Data;

import android.provider.BaseColumns;

public final class UserContract {
    // Constructor privado para prevenir la instanciación
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        // Nombre de la tabla
        public static final String TABLE_NAME = "users";

        // Columnas de la tabla
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";

        public static final String COLUMN_EMAIL = "email";
    }
}
