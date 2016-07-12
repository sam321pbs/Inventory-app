package com.superpak.sammengistu.inventoryapp.model;

public class InventoryItem {

    private String mItemName;
    private int mItemQuantity;
    private String mItemPrice;
    private int mColumnID;
    private byte[] mImageBytes;

    public InventoryItem(String itemName, int itemQuantity, String itemPrice, byte[] imageBytes) {
        mItemName = itemName;
        mItemQuantity = itemQuantity;
        mItemPrice = itemPrice;
        mImageBytes = imageBytes;
    }

    public String getItemName() {
        return mItemName;
    }

    public int getItemQuantity() {
        return mItemQuantity;
    }

    public String getItemPrice() {
        return mItemPrice;
    }

    public void setItemQuantity(int itemQuantity) {
        mItemQuantity = itemQuantity;
    }

    public int getColumnID(){
        return mColumnID;
    }

    public void setColumnID(int columnID) {
        mColumnID = columnID;
    }

    public byte[] getImageBytes() {
        return mImageBytes;
    }
}
