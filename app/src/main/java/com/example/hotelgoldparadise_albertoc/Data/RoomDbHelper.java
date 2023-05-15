package com.example.hotelgoldparadise_albertoc.Data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hotelgoldparadise_albertoc.Room;
import com.example.hotelgoldparadise_albertoc.Data.RoomContract.RoomEntry;

import java.util.ArrayList;

public class RoomDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Rooms.db";

    private static final int DATABASE_VERSION = 2;

    public RoomDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ROOMS_TABLE = "CREATE TABLE " + RoomEntry.TABLE_NAME +
                "("
                + RoomEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RoomEntry.COLUMN_ROOM_NUMBER + " TEXT NOT NULL, "
                + RoomEntry.COLUMN_ROOM_TYPE + " TEXT NOT NULL, "
                + RoomEntry.COLUMN_STATUS + " TEXT NOT NULL, "
                + RoomEntry.COLUMN_CAPACITY + " INTEGER NOT NULL DEFAULT 0, "
                + RoomEntry.COLUMN_PRICE + " DOUBLE NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_ROOMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RoomEntry.TABLE_NAME);
        onCreate(db);
    }


    public long insertarHabitacion(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RoomEntry.COLUMN_ROOM_NUMBER, room.getNumeroHabitacion());
        contentValues.put(RoomEntry.COLUMN_ROOM_TYPE, room.getTipoHabitacion());
        contentValues.put(RoomEntry.COLUMN_STATUS, room.getEstado());
        contentValues.put(RoomEntry.COLUMN_CAPACITY, room.getCapacidad());
        contentValues.put(RoomEntry.COLUMN_PRICE, room.getPrecio());

        long newRowId = db.insert(RoomEntry.TABLE_NAME, null, contentValues);

        return newRowId;
    }

    public ArrayList<Room> displayDataBaseInfo() {
        ArrayList<Room> roomsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                RoomEntry._ID,
                RoomEntry.COLUMN_ROOM_NUMBER,
                RoomEntry.COLUMN_ROOM_TYPE,
                RoomEntry.COLUMN_STATUS,
                RoomEntry.COLUMN_CAPACITY,
                RoomEntry.COLUMN_PRICE
        };

        Cursor cursor = db.query(
                RoomEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String roomNumber = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_ROOM_NUMBER));
            String roomType = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_ROOM_TYPE));
            String status = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_STATUS));
            int capacity = cursor.getInt(cursor.getColumnIndex(RoomEntry.COLUMN_CAPACITY));
            double price = cursor.getDouble(cursor.getColumnIndex(RoomEntry.COLUMN_PRICE));

            Room room = new Room(roomNumber, roomType, status, capacity, price);

            roomsList.add(room);
        }

        cursor.close();
        return roomsList;
    }

    public Room getHabitacionPorNum(String roomNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                RoomEntry._ID,
                RoomEntry.COLUMN_ROOM_NUMBER,
                RoomEntry.COLUMN_ROOM_TYPE,
                RoomEntry.COLUMN_STATUS,
                RoomEntry.COLUMN_CAPACITY,
                RoomEntry.COLUMN_PRICE,
        };

        String selection = RoomEntry.COLUMN_ROOM_NUMBER + "=?";
        String[] selectionArgs = {roomNumber};

        Cursor cursor = db.query(
                RoomEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Room room = null;
        if (cursor.moveToFirst()) {
            String roomNum = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_ROOM_NUMBER));
            String roomType = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_ROOM_TYPE));
            String status = cursor.getString(cursor.getColumnIndex(RoomEntry.COLUMN_STATUS));
            int capacity = cursor.getInt(cursor.getColumnIndex(RoomEntry.COLUMN_CAPACITY));
            double price = cursor.getDouble(cursor.getColumnIndex(RoomEntry.COLUMN_PRICE));

            room = new Room(roomNum, roomType, status, capacity, price);
        }

        cursor.close();
        return room;
    }

    public void actualizarHabitacion(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RoomEntry.COLUMN_ROOM_NUMBER, room.getNumeroHabitacion());
        contentValues.put(RoomEntry.COLUMN_ROOM_TYPE, room.getTipoHabitacion());
        contentValues.put(RoomEntry.COLUMN_STATUS, room.getEstado());
        contentValues.put(RoomEntry.COLUMN_CAPACITY, room.getCapacidad());
        contentValues.put(RoomEntry.COLUMN_PRICE, room.getPrecio());

        String selection = RoomEntry.COLUMN_ROOM_NUMBER + "=?";
        String[] selectionArgs = {room.getNumeroHabitacion()};

        db.update(RoomEntry.TABLE_NAME, contentValues, selection, selectionArgs);
    }

    public void eliminarHabitacion(String roomNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = RoomEntry.COLUMN_ROOM_NUMBER + "=?";
        String[] selectionArgs = {roomNumber};

        db.delete(RoomEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void updateRoom(Room updatedRoom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RoomEntry.COLUMN_ROOM_NUMBER, updatedRoom.getNumeroHabitacion());
        contentValues.put(RoomEntry.COLUMN_ROOM_TYPE, updatedRoom.getTipoHabitacion());
        contentValues.put(RoomEntry.COLUMN_STATUS, updatedRoom.getEstado());
        contentValues.put(RoomEntry.COLUMN_CAPACITY, updatedRoom.getCapacidad());
        contentValues.put(RoomEntry.COLUMN_PRICE, updatedRoom.getPrecio());

        String selection = RoomEntry.COLUMN_ROOM_NUMBER + "=?";
        String[] selectionArgs = {updatedRoom.getNumeroHabitacion()};

        db.update(RoomEntry.TABLE_NAME, contentValues, selection, selectionArgs);
    }

    // Método para eliminar una habitación
    public void deleteRoom(Room room) {
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Define la condición WHERE para la eliminación
        String selection = RoomContract.RoomEntry.COLUMN_ROOM_NUMBER + " LIKE ?";

        // Argumentos para la condición WHERE
        String[] selectionArgs = { room.getNumeroHabitacion() };

        // Realiza la consulta para eliminar la habitación
        db.delete(RoomContract.RoomEntry.TABLE_NAME, selection, selectionArgs);

        // Cierra la conexión a la base de datos
        db.close();
    }


}

