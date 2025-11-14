package com.sahaj.sahaj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahaj.sahaj.R;
import com.sahaj.sahaj.database.Constant;
import com.sahaj.sahaj.database.DBManager;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvOrderAmount;
    private TextView tvDownPayment;
    private TextView tvEmiTerm;
    private TextView tvEmiAmount;
    private TextView tvDpOne;
    private TextView tvDpTwo;
    private TextView tvDpThree;
    private TextView tvDpFour;
    private TextView tvTermOne;
    private TextView tvTermTwo;
    private TextView tvTermThree;
    private RelativeLayout rlDpOne;
    private ImageView ivDpTickOne;
    private ImageView ivDpTickTwo;
    private ImageView ivDpTickThree;
    private ImageView ivDpTickFour;
    private ImageView ivTermTickOne;
    private ImageView ivTermTickTwo;
    private ImageView ivTermTickThree;
    private RelativeLayout rlDpTwo;
    private RelativeLayout rlDpThree;
    private RelativeLayout rlDpFour;
    private RelativeLayout rlTermsOne;
    private RelativeLayout rlTermsTwo;
    private RelativeLayout rlTermsThree;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvOrderAmount = findViewById(R.id.tv_order_amount);
        tvDownPayment = findViewById(R.id.tv_down_payment);
        tvEmiTerm = findViewById(R.id.tv_emi_term);
        tvEmiAmount = findViewById(R.id.tv_emi_amount);
        tvDpOne = findViewById(R.id.tv_dp_1);
        tvDpTwo = findViewById(R.id.tv_dp_2);
        tvDpThree = findViewById(R.id.tv_dp_3);
        tvDpFour = findViewById(R.id.tv_dp_4);
        tvTermOne = findViewById(R.id.tv_terms_1);
        tvTermTwo = findViewById(R.id.tv_terms_2);
        tvTermThree = findViewById(R.id.tv_terms_3);
        rlDpOne = findViewById(R.id.rl_dp_1);
        rlDpTwo = findViewById(R.id.rl_dp_2);
        rlDpThree = findViewById(R.id.rl_dp_3);
        rlDpFour = findViewById(R.id.rl_dp_4);
        ivDpTickOne = findViewById(R.id.iv_blue_tick_dp_1);
        ivDpTickTwo = findViewById(R.id.iv_blue_tick_dp_2);
        ivDpTickThree = findViewById(R.id.iv_blue_tick_dp_3);
        ivDpTickFour = findViewById(R.id.iv_blue_tick_dp_4);
        rlTermsOne = findViewById(R.id.rl_terms_1);
        rlTermsTwo = findViewById(R.id.rl_terms_2);
        rlTermsThree = findViewById(R.id.rl_terms_3);
        ivTermTickOne = findViewById(R.id.iv_blue_tick_mp_1);
        ivTermTickTwo = findViewById(R.id.iv_blue_tick_mp_2);
        ivTermTickThree = findViewById(R.id.iv_blue_tick_mp_3);

        rlDpOne.setOnClickListener(this);
        rlDpTwo.setOnClickListener(this);
        rlDpThree.setOnClickListener(this);
        rlDpFour.setOnClickListener(this);
        rlTermsOne.setOnClickListener(this);
        rlTermsTwo.setOnClickListener(this);
        rlTermsThree.setOnClickListener(this);

        dbManager = new DBManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkValues();
    }

    public void onNext(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void onClose(View view) {
        Intent intent = new Intent(this, ModelActivity.class);
        startActivity(intent);
    }

    private void checkValues(){
        int orderValue = Constant.prices[dbManager.getModelIndex()];
        int dpPercentage = Constant.downTerms[dbManager.getDPIndex()];
        int emiCount = Constant.emiTerms[dbManager.getEMIIndex()];
        int dpAmount = getDownPaymentAmount(orderValue, dpPercentage);
        int emiAmount = getEmiAmount(orderValue, dpAmount, emiCount);

        String orderAmount = "TK " + orderValue;
        String downPayment = "TK " + dpAmount;
        String emiTerm = "Pay in " + Constant.emiTerms[dbManager.getEMIIndex()] + " terms";
        String emi = "TK " + emiAmount;

        tvOrderAmount.setText(orderAmount);
        tvDownPayment.setText(downPayment);
        tvEmiTerm.setText(emiTerm);
        tvEmiAmount.setText(emi);

        checkSelectedDP();
        checkSelectedMP();
    }

    private void checkSelectedDP(){
        int dpIndex = dbManager.getDPIndex();
        rlDpOne.setBackgroundResource(R.drawable.item_background);
        rlDpTwo.setBackgroundResource(R.drawable.item_background);
        rlDpThree.setBackgroundResource(R.drawable.item_background);
        rlDpFour.setBackgroundResource(R.drawable.item_background);
        tvDpOne.setTextColor(getResources().getColor(R.color.black, getTheme()));
        tvDpTwo.setTextColor(getResources().getColor(R.color.black, getTheme()));
        tvDpThree.setTextColor(getResources().getColor(R.color.black, getTheme()));
        tvDpFour.setTextColor(getResources().getColor(R.color.black, getTheme()));
        ivDpTickOne.setVisibility(View.INVISIBLE);
        ivDpTickTwo.setVisibility(View.INVISIBLE);
        ivDpTickThree.setVisibility(View.INVISIBLE);
        ivDpTickFour.setVisibility(View.INVISIBLE);
        switch (dpIndex){
            case 0:
                rlDpOne.setBackgroundResource(R.drawable.item_background_checked);
                tvDpOne.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivDpTickOne.setVisibility(View.VISIBLE);
                break;
            case 1:
                rlDpTwo.setBackgroundResource(R.drawable.item_background_checked);
                tvDpTwo.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivDpTickTwo.setVisibility(View.VISIBLE);
                break;
            case 2:
                rlDpThree.setBackgroundResource(R.drawable.item_background_checked);
                tvDpThree.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivDpTickThree.setVisibility(View.VISIBLE);
                break;
            case 3:
                rlDpFour.setBackgroundResource(R.drawable.item_background_checked);
                tvDpFour.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivDpTickFour.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void checkSelectedMP(){
        int dpIndex = dbManager.getEMIIndex();
        rlTermsOne.setBackgroundResource(R.drawable.item_background);
        rlTermsTwo.setBackgroundResource(R.drawable.item_background);
        rlTermsThree.setBackgroundResource(R.drawable.item_background);
        tvTermOne.setTextColor(getResources().getColor(R.color.black, getTheme()));
        tvTermTwo.setTextColor(getResources().getColor(R.color.black, getTheme()));
        tvTermThree.setTextColor(getResources().getColor(R.color.black, getTheme()));
        ivTermTickOne.setVisibility(View.INVISIBLE);
        ivTermTickTwo.setVisibility(View.INVISIBLE);
        ivTermTickThree.setVisibility(View.INVISIBLE);
        switch (dpIndex){
            case 0:
                rlTermsOne.setBackgroundResource(R.drawable.item_background_checked);
                tvTermOne.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivTermTickOne.setVisibility(View.VISIBLE);
                break;
            case 1:
                rlTermsTwo.setBackgroundResource(R.drawable.item_background_checked);
                tvTermTwo.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivTermTickTwo.setVisibility(View.VISIBLE);
                break;
            case 2:
                rlTermsThree.setBackgroundResource(R.drawable.item_background_checked);
                tvTermThree.setTextColor(getResources().getColor(R.color.primary, getTheme()));
                ivTermTickThree.setVisibility(View.VISIBLE);
                break;
        }
    }

    private int getDownPaymentAmount(int orderValue, int dpPercentage){
        return (orderValue * dpPercentage) / 100;
    }

    private int getEmiAmount(int orderValue, int dpAmount, int emiCount){
        return (orderValue - dpAmount) / emiCount;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_dp_1) {
            dbManager.setDPIndex(0);
        } else if (id == R.id.rl_dp_2) {
            dbManager.setDPIndex(1);
        } else if (id == R.id.rl_dp_3) {
            dbManager.setDPIndex(2);
        } else if (id == R.id.rl_dp_4) {
            dbManager.setDPIndex(3);
        } else if (id == R.id.rl_terms_1) {
            dbManager.setEMIIndex(0);
        } else if (id == R.id.rl_terms_2) {
            dbManager.setEMIIndex(1);
        } else if (id == R.id.rl_terms_3) {
            dbManager.setEMIIndex(2);
        }
        checkValues();
    }
}