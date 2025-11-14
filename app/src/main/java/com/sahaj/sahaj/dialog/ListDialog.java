package com.sahaj.sahaj.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahaj.sahaj.R;
import com.sahaj.sahaj.adapter.ItemListAdapter;
import com.sahaj.sahaj.database.Constant;

public class ListDialog extends Dialog {

    private final String type;

    public ListDialog(String type, Activity context) {
        super(context);
        this.type = type;
        initView(context);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initView(Activity context) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sheet, null);
        TextView tvHeader = dialogView.findViewById(R.id.tv_header);
        String headerText = "Select " + type;
        tvHeader.setText(headerText);
        RecyclerView rvItem = dialogView.findViewById(R.id.rv_item);
        rvItem.setLayoutManager(new LinearLayoutManager(context));
        rvItem.setAdapter(new ItemListAdapter(context, type, getListForType()));
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());
        setContentView(dialogView);
        setCanceledOnTouchOutside(true);
        // Adjust Dialog Height
        Window window = getWindow();
        if(window != null){
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setGravity(Gravity.BOTTOM);
            // Limit height to 30% of screen
            View root = findViewById(R.id.dialog_sheet_root);
            ViewGroup.LayoutParams params = root.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            // Calculate 30% of screen height
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // Set only maxHeight if your content is larger
            params.height = (int) (metrics.heightPixels * 0.3);
            root.setLayoutParams(params);
        }
    }

    private String[] getListForType(){
        if(type.equalsIgnoreCase("Brand")){
            return Constant.brands;
        }else if(type.equalsIgnoreCase("Model")){
            return Constant.models;
        }
        return new String[]{};
    }

}
