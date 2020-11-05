package com.example.counttoearn.Adepter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.counttoearn.Model.TopplayerModel;
import com.example.counttoearn.Model.WithdrawListModel;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.WITHDRAWLIST> {

    public List<WithdrawListModel.Withdrawlist> withdrawlist = new ArrayList<WithdrawListModel.Withdrawlist>();
    Context mcontext;

    public WithdrawAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void addAll(List<WithdrawListModel.Withdrawlist> files) {

        try {
            this.withdrawlist.clear();
            this.withdrawlist.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public void appendAll(List<WithdrawListModel.Withdrawlist> files) {

        try {
            this.withdrawlist.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void clearAll() {
        this.withdrawlist.clear();
        notifyDataSetChanged();
    }

    public void updateList(List<WithdrawListModel.Withdrawlist> list) {
        this.withdrawlist = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WITHDRAWLIST onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.withdraw_list_item, parent, false);
        return new WITHDRAWLIST(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WITHDRAWLIST holder, int position) {


        holder.tv_amount.setText(withdrawlist.get(position).wamount);
        holder.tv_date.setText(parseDateToddMMyyyy(withdrawlist.get(position).wdate));
        holder.tv_options.setText(withdrawlist.get(position).pname);

    }

    public String parseDateToddMMyyyy(String hwdate) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(hwdate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return withdrawlist.size();
    }

    public class WITHDRAWLIST extends RecyclerView.ViewHolder {

        TextView tv_date, tv_options,tv_amount;

        public WITHDRAWLIST(@NonNull View itemView) {
            super(itemView);

            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_options = itemView.findViewById(R.id.tv_options);
        }
    }
}
