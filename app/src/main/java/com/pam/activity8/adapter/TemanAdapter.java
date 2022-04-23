package com.pam.activity8.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pam.activity8.MainActivity;
import com.pam.activity8.R;
import com.pam.activity8.database.DBControleer;
import com.pam.activity8.database.Teman;
import com.pam.activity8.edit_teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listdata;

    public TemanAdapter(ArrayList<Teman> listdata) {
        this.listdata = listdata;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View v = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TemanViewHolder holder, int position) {
        String nm, tlp;

        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();

        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.namaTxt.setTextColor(Color.BLUE);
        holder.telponTxt.setText(tlp);

        holder.kartuku.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listdata != null)?listdata.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView kartuku;
        private TextView namaTxt,telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            kartuku = (CardView) view.findViewById(R.id.kartuku);
                    namaTxt = (TextView)  view.findViewById(R.id.textNama);
                    telponTxt = (TextView)  view.findViewById(R.id.textTelpon);
        }
    }
}
