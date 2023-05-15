package com.example.hotelgoldparadise_albertoc.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hotelgoldparadise_albertoc.User;


public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Añadir usuario de soporte
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, "support");
        values.put(KEY_EMAIL, "a@a.com");
        values.put(KEY_PASSWORD, "esspee"); // Deberías considerar la posibilidad de almacenar las contraseñas de manera segura
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String username, String password, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public User getUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"username", "password", "email"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            String retrievedUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String retrievedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String retrievedEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            user = new User(retrievedUsername, retrievedPassword, retrievedEmail);
        }

        cursor.close();
        return user;
    }


    // Verifica si un usuario con un correo electrónico y contraseña específicos existe en la base de datos
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columnas a devolver
        String[] projection = {
                KEY_USER_ID
        };

        // Cláusula WHERE para filtrar los registros
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }
}



