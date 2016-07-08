package com.superpak.sammengistu.inventoryapp.inventory_db;

import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class InventoryDatabase {

    public static InventoryDBHelper mInventoryDBHelper;

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

            InventoryItem inventoryItem = new InventoryItem(
                cursor.getString(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_NAME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_PRICE)),
                cursor.getString(cursor.getColumnIndex(DatabaseConstants.COLUMN_ITEM_QUANTITY)));

            inventoryItemList.add(inventoryItem);
        }

        return inventoryItemList;
    }

    public static void updateItemCount (InventoryItem inventoryItem, Integer amount, Integer position) {
        SQLiteDatabase database = mInventoryDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.COLUMN_ITEM_NAME, inventoryItem.getItemName());
        contentValues.put(DatabaseConstants.COLUMN_ITEM_QUANTITY, amount);
        contentValues.put(DatabaseConstants.COLUMN_ITEM_PRICE, inventoryItem.getItemPrice());

        database.update(DatabaseConstants.INVENTORY_TABLE_NAME, contentValues,
            DatabaseConstants.COLUMN_ID + "=" + position, null);
    }

}
