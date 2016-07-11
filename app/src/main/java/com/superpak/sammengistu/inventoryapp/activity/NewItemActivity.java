package com.superpak.sammengistu.inventoryapp.activity;

import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewItemActivity extends AppCompatActivity {

    private EditText mNameOfItem;
    private EditText mQuantityOfItem;
    private EditText mPriceOfItem;
    private Button mDone;

    @Override
    protected void onCreate(Bundle onSave){
        super.onCreate(onSave);
        setContentView(R.layout.new_item_activity);

        mNameOfItem = (EditText) findViewById(R.id.name_EditText);
        mQuantityOfItem = (EditText) findViewById(R.id.amount_editText3);
        mPriceOfItem = (EditText) findViewById(R.id.price_editText2);
        mDone = (Button) findViewById(R.id.done_button);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areAllFieldsEntered()){
                    InventoryDatabase.mInventoryDBHelper.getWritableDatabase();
                    InventoryItem inventoryItem = new InventoryItem(mNameOfItem.getText().toString(),
                        Integer.parseInt(mQuantityOfItem.getText().toString()),
                        mPriceOfItem.getText().toString());

                    InventoryDatabase.addNewItem(inventoryItem);

                    Intent intent = new Intent(NewItemActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean areAllFieldsEntered() {
        return !mNameOfItem.getText().toString().equals("") &&
            !mQuantityOfItem.getText().toString().equals("") &&
        !mPriceOfItem.getText().toString().equals("");
    }
}
