package com.sahaj.sahaj.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sahaj.sahaj.R;
import com.sahaj.sahaj.database.DBManager;
import com.sahaj.sahaj.listener.DialogListener;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder>{

    private final String[] itemList;
    private final String type;
    private final DBManager dbManager;
    private final DialogListener listener;

    public ItemListAdapter(Activity context, String type, String[] itemList) {
        this.type = type;
        this.itemList = itemList;
        this.dbManager = new DBManager(context);
        this.listener = (DialogListener) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String currentItem = itemList[position];
        holder.tvItem.setText(currentItem);
        holder.rlRoot.setOnClickListener(v -> {
            if(type.equalsIgnoreCase("Brand")){
                dbManager.setBrandIndex(position);
            }else if(type.equalsIgnoreCase("Model")){
                dbManager.setModelIndex(position);
            }
            listener.onDismiss();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlRoot;
        public TextView tvItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlRoot = itemView.findViewById(R.id.rl_item_root);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}


