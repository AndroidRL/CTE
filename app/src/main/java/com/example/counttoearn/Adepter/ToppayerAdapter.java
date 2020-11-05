package com.example.counttoearn.Adepter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.counttoearn.Activity.MainActivity;
import com.example.counttoearn.Model.TopplayerModel;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToppayerAdapter extends RecyclerView.Adapter<ToppayerAdapter.TOPPALAYERLIST> {

    public List<TopplayerModel.Topplayer> topplayers = new ArrayList<TopplayerModel.Topplayer>();
    Context mcontext;

    public ToppayerAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void addAll(List<TopplayerModel.Topplayer> files) {

        try {
            this.topplayers.clear();
            this.topplayers.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public void appendAll(List<TopplayerModel.Topplayer> files) {

        try {
            this.topplayers.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void clearAll() {
        this.topplayers.clear();
        notifyDataSetChanged();
    }

    public void updateList(List<TopplayerModel.Topplayer> list) {
        this.topplayers = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TOPPALAYERLIST onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.topplayer_item, parent, false);
        return new TOPPALAYERLIST(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TOPPALAYERLIST holder, int position) {

        holder.name.setText(topplayers.get(position).name);
        holder.coin.setText(topplayers.get(position).coin);

        Uri profilePic = Uri.parse(RestClient.IMAGE_URL + topplayers.get(position).profile_pic);

            Glide.with(mcontext).load(profilePic).into(holder.profile_img);


    }

    @Override
    public int getItemCount() {
        return topplayers.size();
    }

    public class TOPPALAYERLIST extends RecyclerView.ViewHolder {

        CircleImageView profile_img;
        TextView name, coin;

        public TOPPALAYERLIST(@NonNull View itemView) {
            super(itemView);

            profile_img = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            coin = itemView.findViewById(R.id.coin);
        }
    }
}
