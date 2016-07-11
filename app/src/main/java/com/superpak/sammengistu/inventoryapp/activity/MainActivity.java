package com.superpak.sammengistu.inventoryapp.activity;

import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.adapter.InventoryAdapter;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDBHelper;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity55";

    private Button mAddNewItemToDBButton;
    private ListView mInventoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInventoryListView = (ListView) findViewById(android.R.id.list);

        InventoryDatabase.mInventoryDBHelper = new InventoryDBHelper(this);

        mInventoryListView.setAdapter(new InventoryAdapter(
            this, R.layout.list_item_view));

        mInventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<InventoryItem> inventoryItems = InventoryDatabase.getAllInventoryItems();

                Log.i(TAG, "Quantity = " + inventoryItems.get(position).getItemQuantity());
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.QUANTITY, inventoryItems.get(position).getItemQuantity());
                intent.putExtra(DetailActivity.ID, inventoryItems.get(position).getColumnID());
                intent.putExtra(DetailActivity.PRICE, inventoryItems.get(position).getItemPrice());
                intent.putExtra(DetailActivity.NAME, inventoryItems.get(position).getItemName());

                startActivity(intent);
            }
        });

        mAddNewItemToDBButton = (Button) findViewById(R.id.add_item_to_inventory);

        mAddNewItemToDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                startActivity(intent);
            }
        });
    }
}
