package com.superpak.sammengistu.inventoryapp.model;

/**
 * Created by SamMengistu on 7/8/16.
 */
public class InventoryItem {

    private String mItemName;
    private int mItemQuantity;
    private String mItemPrice;

    public InventoryItem(String itemName, int itemQuantity, String itemPrice) {
        mItemName = itemName;
        mItemQuantity = itemQuantity;
        mItemPrice = itemPrice;
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
}
