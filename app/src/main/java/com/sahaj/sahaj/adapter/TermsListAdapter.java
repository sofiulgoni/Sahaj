package com.sahaj.sahaj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahaj.sahaj.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TermsListAdapter extends RecyclerView.Adapter<TermsListAdapter.MyViewHolder> {

    int emiAmount;
    int emiCount;

    public TermsListAdapter(int emiAmount, int emiCount) {
        this.emiAmount = emiAmount;
        this.emiCount = emiCount;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermsListAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 30);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

        cal.add(Calendar.MONTH, position);

        String date = sdf.format(cal.getTime());
        holder.tvDate.setText(date);

        if(position == 0){
            holder.viewTopLine.setVisibility(View.INVISIBLE);
        }else if(position == emiCount - 1){
            holder.viewBottomLine.setVisibility(View.INVISIBLE);
        }

        String amount = "TK " + emiAmount;
        holder.tvAmount.setText(amount);

        String term = (position + 1) + getTermSuffix(position+1) + " term";
        holder.tvTerms.setText(term);

    }

    @Override
    public int getItemCount() {
        return emiCount;
    }

    private String getTermSuffix(int n) {
        if (n % 10 == 1 && n != 11) return "st";
        if (n % 10 == 2 && n != 12) return "nd";
        if (n % 10 == 3 && n != 13) return "rd";
        return "th";
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public View viewTopLine;
        public View viewBottomLine;
        public TextView tvAmount;
        public TextView tvTerms;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            viewTopLine = itemView.findViewById(R.id.view_top_line);
            viewBottomLine = itemView.findViewById(R.id.view_bottom_line);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvTerms = itemView.findViewById(R.id.tv_terms);
        }
    }
}
