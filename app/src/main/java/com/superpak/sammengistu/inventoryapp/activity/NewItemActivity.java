package com.superpak.sammengistu.inventoryapp.activity;

import com.superpak.sammengistu.inventoryapp.DbBitmapUtility;
import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class NewItemActivity extends AppCompatActivity {

    private EditText mNameOfItem;
    private EditText mQuantityOfItem;
    private EditText mPriceOfItem;
    private Button mDone;
    private Button mTakePhotoButton;
    private ImageView mPhotoView;
    private Bitmap mBitmapPhoto;

    private boolean mIsPhotoTaken;

    private static final int CAMERA_REQUEST = 55;

    @Override
    protected void onCreate(Bundle onSave){
        super.onCreate(onSave);
        setContentView(R.layout.new_item_activity);

        mIsPhotoTaken = false;
        mNameOfItem = (EditText) findViewById(R.id.name_EditText);
        mQuantityOfItem = (EditText) findViewById(R.id.amount_editText3);
        mPriceOfItem = (EditText) findViewById(R.id.price_editText2);
        mDone = (Button) findViewById(R.id.done_button);
        mTakePhotoButton = (Button) findViewById(R.id.take_photo);
        mPhotoView = (ImageView) findViewById(R.id.photo_view);

        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areAllFieldsEntered() && mIsPhotoTaken){
                    InventoryItem inventoryItem = new InventoryItem(
                        mNameOfItem.getText().toString(),
                        Integer.parseInt(mQuantityOfItem.getText().toString()),
                        mPriceOfItem.getText().toString(),
                        DbBitmapUtility.getBytes(mBitmapPhoto));

                    InventoryDatabase.addNewItem(inventoryItem);

                    Intent intent = new Intent(NewItemActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST){

            if (data != null) {
                mIsPhotoTaken = true;
                mBitmapPhoto = (Bitmap) data.getExtras().get("data");
                mPhotoView.setImageBitmap(mBitmapPhoto);
            }
        }
    }

    private boolean areAllFieldsEntered() {
        return !mNameOfItem.getText().toString().equals("") &&
            !mQuantityOfItem.getText().toString().equals("") &&
        !mPriceOfItem.getText().toString().equals("");
    }
}
