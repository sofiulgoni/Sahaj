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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahaj.sahaj.R;
import com.sahaj.sahaj.adapter.TermsListAdapter;
import com.sahaj.sahaj.database.Constant;
import com.sahaj.sahaj.database.DBManager;

public class OrderActivity extends AppCompatActivity {

    private TextView tvEmiTerm;
    private TextView tvTotalPrice;
    private TextView tvDownPayment;
    private TextView tvTotalOutstanding;
    private TextView tvRepaymentPlan;
    private RecyclerView rvTerms;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvEmiTerm = findViewById(R.id.tv_emi_term);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvDownPayment = findViewById(R.id.tv_down_payment);
        tvTotalOutstanding = findViewById(R.id.tv_total_outstanding);
        tvRepaymentPlan = findViewById(R.id.tv_total_terms);
        rvTerms = findViewById(R.id.rv_terms);

        dbManager = new DBManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkValues();
    }

    public void onConfirm(View view) {
        Intent intent = new Intent(this, ModelActivity.class);
        startActivity(intent);
    }

    public void onClose(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }

    private void checkValues(){
        int orderValue = Constant.prices[dbManager.getModelIndex()];
        int dpPercentage = Constant.downTerms[dbManager.getDPIndex()];
        int emiCount = Constant.emiTerms[dbManager.getEMIIndex()];
        int dpAmount = getDownPaymentAmount(orderValue, dpPercentage);
        int outstandingAmount = getOutstandingAmount(orderValue, dpAmount);
        int emiAmount = getEmiAmount(orderValue, dpAmount, emiCount);

        String paymentTerm = emiCount + " Months";
        String totalPrice = "TK " + orderValue;
        String downPayment = "TK " + dpAmount;
        String dueAmount = "TK " + outstandingAmount;
        String repaymentPlan = "Total " + emiCount + " terms";

        tvEmiTerm.setText(paymentTerm);
        tvTotalPrice.setText(totalPrice);
        tvDownPayment.setText(downPayment);
        tvTotalOutstanding.setText(dueAmount);
        tvRepaymentPlan.setText(repaymentPlan);

        rvTerms.setLayoutManager(new LinearLayoutManager(this));
        rvTerms.setAdapter(new TermsListAdapter(emiAmount, emiCount));
    }

    private int getDownPaymentAmount(int orderValue, int dpPercentage){
        return (orderValue * dpPercentage) / 100;
    }

    private int getEmiAmount(int orderValue, int dpAmount, int emiCount){
        return (orderValue - dpAmount) / emiCount;
    }

    private int getOutstandingAmount(int orderValue, int dpAmount){
        return orderValue - dpAmount;
    }
}