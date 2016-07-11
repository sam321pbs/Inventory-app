package com.superpak.sammengistu.inventoryapp.adapter;

import com.superpak.sammengistu.inventoryapp.R;
import com.superpak.sammengistu.inventoryapp.inventory_db.InventoryDatabase;
import com.superpak.sammengistu.inventoryapp.model.InventoryItem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class InventoryAdapter extends ArrayAdapter{

    private final String TAG = "InventoryAdapter55";
    private Context mContext;
    private List<InventoryItem> mInventoryItems;

    public InventoryAdapter(Context context, int resource) {
        super(context, resource, InventoryDatabase.getAllInventoryItems());
        mContext = context;
        mInventoryItems = InventoryDatabase.getAllInventoryItems();
    }

    @Override
    public int getCount() {
        return mInventoryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mInventoryItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        InventoryViewHolder viewHolder = new InventoryViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_view, parent, false);

            viewHolder.mInventoryItemName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.mInventoryItemPrice = (TextView) convertView.findViewById(R.id.item_price);
            viewHolder.mInventoryItemQuantity = (TextView) convertView.findViewById(R.id.item_quantity);

            viewHolder.mInventoryAdd = (TextView) convertView.findViewById(R.id.add);
            viewHolder.mInventoryRemove = (TextView) convertView.findViewById(R.id.remove);
            viewHolder.mInventoryDelete = (TextView) convertView.findViewById(R.id.delete);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (InventoryViewHolder) convertView.getTag();
        }

        final InventoryItem inventoryItem = mInventoryItems.get(position);

        final InventoryViewHolder temp = viewHolder;

        viewHolder.mInventoryItemName.setText(inventoryItem.getItemName());
        viewHolder.mInventoryItemPrice.setText(inventoryItem.getItemPrice());
        String quantityAmount = inventoryItem.getItemQuantity() + "";
        viewHolder.mInventoryItemQuantity.setText(quantityAmount);
        viewHolder.mInventoryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "delete = " + mInventoryItems.get(position).getColumnID());
                InventoryDatabase.deleteItem(mInventoryItems.get(position).getColumnID());
                mInventoryItems.remove(position);
                InventoryAdapter.this.notifyDataSetChanged();
            }
        });

        viewHolder.mInventoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = inventoryItem.getItemQuantity() + 1;
                mInventoryItems.get(position).setItemQuantity(amount);
                Log.i(TAG, "amount = " + amount);
                String amountText = amount + "";
                temp.mInventoryItemQuantity.setText(amountText);
                InventoryDatabase.updateItemCount(inventoryItem, amount);
            }
        });

        viewHolder.mInventoryRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = inventoryItem.getItemQuantity() - 1;
                if (amount < 0) {
                    amount = 0;
                }
                Log.i(TAG, "amount = " + amount);
                mInventoryItems.get(position).setItemQuantity(amount);

                String amountText = amount + "";
                temp.mInventoryItemQuantity.setText(amountText);

                InventoryDatabase.updateItemCount(inventoryItem, amount);
            }
        });

        return convertView;
    }

    static class InventoryViewHolder {
        TextView mInventoryItemName;
        TextView mInventoryItemQuantity;
        TextView mInventoryItemPrice;
        TextView mInventoryAdd;
        TextView mInventoryRemove;
        TextView mInventoryDelete;

    }
}
