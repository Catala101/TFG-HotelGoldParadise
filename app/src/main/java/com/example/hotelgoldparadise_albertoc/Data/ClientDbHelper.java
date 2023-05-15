package com.example.hotelgoldparadise_albertoc.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hotelgoldparadise_albertoc.Cliente;
import com.example.hotelgoldparadise_albertoc.Data.ClientContract.ClientEntry;

import java.util.ArrayList;

public class ClientDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clientes.db";

    private static final int DATABASE_VERSION = 1;

    public ClientDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public long insertClient(Cliente client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClientEntry.COLUMN_CLIENT_NAME, client.getNombre());
        contentValues.put(ClientEntry.COLUMN_CLIENT_DNI, client.getDni());
        contentValues.put(ClientEntry.COLUMN_CLIENT_RESIDENTS, client.getNum_residentes());
        contentValues.put(ClientEntry.COLUMN_CLIENT_DAYS, client.getNum_dias());
        contentValues.put(ClientEntry.COLUMN_CLIENT_ROOM, client.getHabitacion());
        contentValues.put(ClientEntry.COLUMN_CLIENT_N_ROOM, client.getNum_habitacion());
        contentValues.put(ClientEntry.COLUMN_CLIENT_PRICE, client.getPrecio());

        long newRowId= db.insert(ClientEntry.TABLE_NAME, null, contentValues);

        return newRowId;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       String SQL_CREATE_CLIENTS_TABLE = "CREATE TABLE " + ClientContract.ClientEntry.TABLE_NAME +
                "("
                + ClientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ClientEntry.COLUMN_CLIENT_NAME + " TEXT NOT NULL, "
                + ClientEntry.COLUMN_CLIENT_DNI + " TEXT NOT NULL, "
                + ClientEntry.COLUMN_CLIENT_RESIDENTS + " INTEGER NOT NULL DEFAULT 0, "
                + ClientEntry.COLUMN_CLIENT_DAYS + " INTEGER NOT NULL DEFAULT 0, "
                + ClientEntry.COLUMN_CLIENT_ROOM + " TEXT NOT NULL, "
                + ClientEntry.COLUMN_CLIENT_N_ROOM + " INTEGER NOT NULL DEFAULT 0, "
                + ClientEntry.COLUMN_CLIENT_PRICE + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_CLIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Introducimos el dni del cliente y nos devuelve todos los datos de ese cliente
    public void buscarCliente(Cliente busquedaClientes, String editBuscarDni){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery( "SELECT * FROM Clientes WHERE dni='"+editBuscarDni+"'", null);
        if(cursor.moveToFirst()){
            do{
                busquedaClientes.setNombre(cursor.getString( 1));
                busquedaClientes.setDni(cursor.getString( 2));
                busquedaClientes.setNum_residentes(cursor.getString( 3));
                busquedaClientes.setNum_dias(cursor.getString( 4));
                busquedaClientes.setHabitacion(cursor.getString( 5));
                busquedaClientes.setNum_habitacion(cursor.getString( 6));
                busquedaClientes.setPrecio(cursor.getString( 7));
            }while(cursor.moveToNext());
        }
    }
    //Introducimos el dni del cliente y nos devuelve todos los datos de ese cliente para poder modificarlos
    public void updateClient(Cliente client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClientEntry.COLUMN_CLIENT_NAME, client.getNombre());
        contentValues.put(ClientEntry.COLUMN_CLIENT_DNI, client.getDni());
        contentValues.put(ClientEntry.COLUMN_CLIENT_RESIDENTS, client.getNum_residentes());
        contentValues.put(ClientEntry.COLUMN_CLIENT_DAYS, client.getNum_dias());
        contentValues.put(ClientEntry.COLUMN_CLIENT_ROOM, client.getHabitacion());
        contentValues.put(ClientEntry.COLUMN_CLIENT_N_ROOM, client.getNum_habitacion());
        contentValues.put(ClientEntry.COLUMN_CLIENT_PRICE, client.getPrecio());

        db.update(ClientEntry.TABLE_NAME, contentValues, ClientEntry.COLUMN_CLIENT_DNI + " = ?", new String[]{client.getDni()});
    }


    public void buscarNumHabitacion(Cliente clientesHabitacion, String numHabitacionDevuelto){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery( "SELECT * FROM Clientes WHERE num_habitacion='"+numHabitacionDevuelto+"'", null);
        if(cursor.moveToFirst()){
            do{
                clientesHabitacion.setNombre(cursor.getString( 1));
                clientesHabitacion.setDni(cursor.getString( 2));
                clientesHabitacion.setNum_residentes(cursor.getString( 3));
                clientesHabitacion.setNum_dias(cursor.getString( 4));
                clientesHabitacion.setHabitacion(cursor.getString( 5));
                clientesHabitacion.setNum_habitacion(cursor.getString( 6));
                clientesHabitacion.setPrecio(cursor.getString( 7));
            }while(cursor.moveToNext());
        }
    }
    public void editarCliente(String dnientry, String name, String dni, String residents, String days, String room, String num_room, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db!=null){
            db.execSQL("UPDATE Clientes SET nombre='"+name+"',dni='"+dni+"',num_residentes='"+residents+"',num_dias='"+days+"',habitacion='"+room+"',num_habitacion='"+num_room+"',precio='"+price+"' WHERE dni='"+dnientry+"'");
            db.close();
        }
    }
    public void eliminarCliente(String dnientry){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db!=null){
            db.execSQL("DELETE FROM Clientes where dni='"+dnientry+"'");
            db.close();
        }
    }
    public ArrayList<Cliente> displayDataBaseInfo (){
        ArrayList<Cliente> myClientArray = new ArrayList<Cliente>();
        SQLiteDatabase db = this.getReadableDatabase();

        //La projection nos va a indicar las columnas de la tabla que nos interesa consultar.
                String [] projection = {
                ClientEntry._ID,
                ClientEntry.COLUMN_CLIENT_NAME,
                ClientEntry.COLUMN_CLIENT_DNI,
                ClientEntry.COLUMN_CLIENT_RESIDENTS,
                ClientEntry.COLUMN_CLIENT_DAYS,
                ClientEntry.COLUMN_CLIENT_ROOM,
                ClientEntry.COLUMN_CLIENT_N_ROOM,
                ClientEntry.COLUMN_CLIENT_PRICE,

        };

        Cursor cursor = db.query(
                ClientEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //Obtenemos los índices de nuestras columnas.
        int nameColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_NAME);
        int dniColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_DNI);
        int residentColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_RESIDENTS);
        int daysColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_DAYS);
        int roomColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_ROOM);
        int nRoomColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_N_ROOM);
        int priceColum = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_PRICE);

        //Con cada uno de los índices ya podemos recorrer las filas.
        while(cursor.moveToNext()){
            String currentName = cursor.getString(nameColum);
            String currentDni = cursor.getString(dniColum);
            String currentResident = cursor.getString(residentColum);
            String currentDays = cursor.getString(daysColum);
            String currentRoom = cursor.getString(roomColum);
            String currentnRoom = cursor.getString(nRoomColum);
            String currentPrice = cursor.getString(priceColum);



            if(currentName.isEmpty() || currentDni.isEmpty() || currentResident.isEmpty() || currentDays.isEmpty() || currentRoom.isEmpty() || currentnRoom.isEmpty() || currentPrice.isEmpty()){
                myClientArray.add(null);
            }else{
                Cliente currentClient = new Cliente(currentName, currentDni, currentResident, currentDays, currentRoom, currentnRoom, currentPrice);
                myClientArray.add(currentClient);
            }

        }

        return myClientArray;
    }

}
