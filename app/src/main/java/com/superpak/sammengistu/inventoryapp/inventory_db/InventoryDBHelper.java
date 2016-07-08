package com.superpak.sammengistu.inventoryapp.inventory_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class InventoryDBHelper extends SQLiteOpenHelper {

    private final String CREATE = "CREATE TABLE " + DatabaseConstants.INVENTORY_TABLE_NAME
        + " ( "
        + DatabaseConstants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + DatabaseConstants.COLUMN_ITEM_NAME + " TEXT,"
        + DatabaseConstants.COLUMN_ITEM_QUANTITY + " REAL,"
        + DatabaseConstants.COLUMN_ITEM_PRICE + " TEXT)";

    public InventoryDBHelper(Context context) {
        super(context, DatabaseConstants.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CREATE);
    }
}
