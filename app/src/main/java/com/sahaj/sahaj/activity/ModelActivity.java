package com.sahaj.sahaj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahaj.sahaj.R;
import com.sahaj.sahaj.database.Constant;
import com.sahaj.sahaj.database.DBManager;
import com.sahaj.sahaj.dialog.ListDialog;
import com.sahaj.sahaj.listener.DialogListener;

public class ModelActivity extends AppCompatActivity implements DialogListener {

    private TextView tvBrand;
    private TextView tvModel;
    private TextView tvPrice;
    private ListDialog dialog;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_model);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvBrand = findViewById(R.id.tv_brand);
        tvModel = findViewById(R.id.tv_model);
        tvPrice = findViewById(R.id.tv_price);

        dbManager = new DBManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkValues();
    }

    public void onBrandClick(View view) {
        dialog = new ListDialog("Brand", this);
        dialog.show();
    }

    public void onModelClick(View view) {
        dialog = new ListDialog("Model", this);
        dialog.show();
    }

    public void onNext(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }

    private void checkValues(){
        String brand = Constant.brands[dbManager.getBrandIndex()];
        String model = Constant.models[dbManager.getModelIndex()];
        String price = "TK " + Constant.prices[dbManager.getModelIndex()];

        tvBrand.setText(brand);
        tvModel.setText(model);
        tvPrice.setText(price);
    }

    @Override
    public void onDismiss() {
        dialog.dismiss();
        checkValues();
    }
}