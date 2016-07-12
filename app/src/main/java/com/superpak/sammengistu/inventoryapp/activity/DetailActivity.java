package com.superpak.sammengistu.inventoryapp.activity;

import com.superpak.sammengistu.inventoryapp.DbBitmapUtility;
import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String PRICE = "price";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String ID = "id";
    private static final String TAG = "DetailActivity55";
    public static final String PHOTO = "photo";

    private TextView mDetailPriceTextView;
    private TextView mDetailNameTextView;
    private TextView mDetailQuantityTextView;
    private Button mDetailAddOne;
    private Button mDetailMinusOne;
    private Button mDeleteItem;
    private Button mOrder;
    private EditText mOrderAmountEditText;
    private EditText mDetailDecreaseEditText;
    private Button mDetailDecreaseButton;
    private ImageView mPhotoImageView;
    private Button mOrderMore;

    private InventoryItem mInventoryItem;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.detail_view_activity);

        mInventoryItem = new InventoryItem(getIntent().getStringExtra(NAME),
            getIntent().getIntExtra(QUANTITY, 0),
            getIntent().getStringExtra(PRICE),
            getIntent().getByteArrayExtra(PHOTO));
        mInventoryItem.setColumnID(getIntent().getIntExtra(ID, 0));

        initializeViews();
        setUpTextView();
        setOnClickListeners();

        mPhotoImageView.setImageBitmap(DbBitmapUtility.getImage(mInventoryItem.getImageBytes()));
    }

    private void setOnClickListeners() {
        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DetailActivity.this)
                    .setTitle(DetailActivity.this.getString(R.string.delete))
                    .setMessage(
                        DetailActivity.this.getString(R.string.are_you_sure_you_want_to_delete))
                    .setPositiveButton(DetailActivity.this.getString(R.string.delete),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                InventoryDatabase.deleteItem(getIntent().getIntExtra(ID, 0));
                                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
            }
        });

        mDetailAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = mInventoryItem.getItemQuantity() + 1;
                mInventoryItem.setItemQuantity(amount);
                String amountText = amount + "";
                mDetailQuantityTextView.setText(amountText);
                InventoryDatabase.updateItemCount(mInventoryItem, amount);
            }
        });

        mDetailMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = mInventoryItem.getItemQuantity() - 1;
                if (amount < 0) {
                    amount = 0;
                }
                mInventoryItem.setItemQuantity(amount);

                String amountText = amount + "";
                mDetailQuantityTextView.setText(amountText);

                InventoryDatabase.updateItemCount(mInventoryItem, amount);
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountOrder = mOrderAmountEditText.getText().toString();
                if (!amountOrder.equals("")) {
                    int order = mInventoryItem.getItemQuantity() +
                        Integer.parseInt(amountOrder);
                    mInventoryItem.setItemQuantity(order);

                    mDetailQuantityTextView.setText(order + "");
                    InventoryDatabase.updateItemCount(mInventoryItem, order);
                }
            }
        });

        mDetailDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountOrder = mDetailDecreaseEditText.getText().toString();
                if (!amountOrder.equals("") &&
                    Integer.parseInt(amountOrder) <=
                        Integer.parseInt(mDetailQuantityTextView.getText().toString())) {
                    int order = mInventoryItem.getItemQuantity() -
                        Integer.parseInt(amountOrder);
                    mInventoryItem.setItemQuantity(order);

                    mDetailQuantityTextView.setText(order + "");
                    InventoryDatabase.updateItemCount(mInventoryItem, order);
                }
            }
        });

        mOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, "email@email.com");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Order more");
                startActivity(sendIntent);

            }
        });
    }

    private void setUpTextView() {
        mDetailPriceTextView.setText(mInventoryItem.getItemPrice());
        mDetailNameTextView.setText(mInventoryItem.getItemName());
        mDetailQuantityTextView.setText(mInventoryItem.getItemQuantity() + "");
    }

    private void initializeViews() {
        mDetailPriceTextView = (TextView) findViewById(R.id.price_textView2);
        mDetailNameTextView = (TextView) findViewById(R.id.nameTextView);
        mDetailQuantityTextView = (TextView) findViewById(R.id.quantity_textView4);

        mDeleteItem = (Button) findViewById(R.id.delete_item);
        mDetailAddOne = (Button) findViewById(R.id.add_one);
        mDetailMinusOne = (Button) findViewById(R.id.minus_one);
        mOrder = (Button) findViewById(R.id.order);
        mOrderAmountEditText = (EditText) findViewById(R.id.order_edittext);
        mDetailDecreaseEditText = (EditText) findViewById(R.id.decrease_edittext);
        mDetailDecreaseButton = (Button) findViewById(R.id.sale_order);
        mPhotoImageView = (ImageView) findViewById(R.id.photo_product_detail);
        mOrderMore = (Button) findViewById(R.id.order_more);
    }
}
