package com.superpak.sammengistu.inventoryapp.inventory_db;

import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class InventoryDatabase {

    public static InventoryDBHelper mInventoryDBHelper;
    private static final String TAG = "InventoryDatabase55";

    public static void addNewItem(InventoryItem inventoryItem) {
        SQLiteDatabase database = mInventoryDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.COLUMN_ITEM_NAME, inventoryItem.getItemName());
        contentValues.put(DatabaseConstants.COLUMN_ITEM_QUANTITY, inventoryItem.getItemQuantity());
        contentValues.put(DatabaseConstants.COLUMN_ITEM_PRICE, inventoryItem.getItemPrice());

        database.insert(DatabaseConstants.INVENTORY_TABLE_NAME, null, contentValues);

        database.close();
    }

    public static List<InventoryItem> getAllInventoryItems() {
        SQLiteDatabase sqLiteDatabase = mInventoryDBHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
            DatabaseConstants.INVENTORY_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null);

        return cursorToInventoryItems(cursor);
    }

    private static List<InventoryItem> cursorToInventoryItems(Cursor cursor) {
        cursor.moveToFirst();

        List<InventoryItem> inventoryItemList = new ArrayList<>();

        while (cursor.moveToNext()){

            Log.i(TAG, "Id = " + cursor.getInt(cursor.getColumnIndex(DatabaseConstants.COLUMN_ID)));
            InventoryItem inventoryItem = new InventoryItem(
                cursor.getString(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_NAME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_PRICE)));

            inventoryItemList.add(inventoryItem);
        }

        return inventoryItemList;
    }

    public static void updateItemCount (InventoryItem inventoryItem, Integer amount, Integer position) {
        SQLiteDatabase database = mInventoryDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.COLUMN_ID, position);
        contentValues.put(DatabaseConstants.COLUMN_ITEM_NAME, inventoryItem.getItemName());
        contentValues.put(DatabaseConstants.COLUMN_ITEM_QUANTITY, amount);
        contentValues.put(DatabaseConstants.COLUMN_ITEM_PRICE, inventoryItem.getItemPrice());

        Log.i(TAG, "pos = " + position);
        database.update(DatabaseConstants.INVENTORY_TABLE_NAME, contentValues,
            DatabaseConstants.COLUMN_ID + "=" + position, null);
    }

}
