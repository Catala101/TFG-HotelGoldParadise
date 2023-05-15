package com.example.hotelgoldparadise_albertoc.Data;


import android.provider.BaseColumns;

public class RoomContract {

    private RoomContract() {

    }

    public static final class RoomEntry implements BaseColumns {

        public final static String TABLE_NAME = "Rooms";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ROOM_NUMBER = "room_number";
        public final static String COLUMN_ROOM_TYPE = "room_type";
        public final static String COLUMN_STATUS = "status";
        public final static String COLUMN_CAPACITY = "capacity";

        public final static String COLUMN_PRICE = "price";

    }
}

