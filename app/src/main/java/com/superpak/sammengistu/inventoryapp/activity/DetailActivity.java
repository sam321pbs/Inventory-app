package com.superpak.sammengistu.inventoryapp.activity;

import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String PRICE = "price";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String ID = "id";
    private static final String TAG = "DetailActivity55";

    private TextView mDetailPriceTextView;
    private TextView mDetailNameTextView;
    private TextView mDetailQuantityTextView;
    private Button mDetailAddOne;
    private Button mDetailMinusOne;
    private Button mDeleteItem;
    private Button mOrder;
    private EditText mOrderAmountEditText;

    private InventoryItem mInventoryItem;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.detail_view_activity);

        mInventoryItem = new InventoryItem(getIntent().getStringExtra(NAME),
            getIntent().getIntExtra(QUANTITY, 0),
            getIntent().getStringExtra(PRICE));
        mInventoryItem.setColumnID(getIntent().getIntExtra(ID, 0));

        Log.i(TAG, "Item name = " + mInventoryItem.getItemName() + " price + "
            + mInventoryItem.getItemPrice() + " quantity = " + mInventoryItem.getItemQuantity());

        initializeViews();
        setUpTextView();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryDatabase.deleteItem(getIntent().getIntExtra(ID, 0));
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
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
                    mInventoryItem.setItemQuantity(mInventoryItem.getItemQuantity() +
                        Integer.parseInt(amountOrder));

                    mDetailQuantityTextView.setText(amountOrder);
                    InventoryDatabase.updateItemCount(mInventoryItem, Integer.parseInt(amountOrder));

                }
            }
        });
    }

    private void setUpTextView() {
        mDetailPriceTextView.setText(mInventoryItem.getItemPrice());
        mDetailNameTextView.setText(mInventoryItem.getItemName());
        mDetailQuantityTextView.setText(mInventoryItem.getItemQuantity());
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
    }
}
