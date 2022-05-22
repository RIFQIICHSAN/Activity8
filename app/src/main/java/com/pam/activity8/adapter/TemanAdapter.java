package com.pam.activity8.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pam.activity8.EditTeman;
import com.pam.activity8.MainActivity;
import com.pam.activity8.R;
import com.pam.activity8.app.AppController;
import com.pam.activity8.database.Teman;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listdata;

    public TemanAdapter(ArrayList<Teman> listdata) {
        this.listdata = listdata;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemanViewHolder holder, int position) {
        String id, nm, tlp;

        id = listdata.get(position).getId();
        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();

        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.namaTxt.setTextColor(Color.BLUE);
        holder.telponTxt.setText(tlp);

        holder.kartuku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm = new PopupMenu(v.getContext(), v);
                pm.inflate(R.menu.menu);

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem Item) {
                        switch (Item.getItemId())
                        {
                            case R.id.Edit:
                                Bundle bendel = new Bundle();
                                bendel.putString("kunci1", id);
                                bendel.putString("kunci2", nm);
                                bendel.putString("kunci3", tlp);
                                Intent inten = new Intent(v.getContext(), EditTeman.class);
                                inten.putExtras(bendel);
                                v.getContext().startActivity(inten);
                                break;

                            case R.id.Hapus:
                                AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                alertdb.setTitle("Yakin "+nm+" akan dihapus?");
                                alertdb.setMessage("Tekan Ya untuk menghapus");
                                alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HapusData(id);
                                        Toast.makeText(v.getContext(), "Data "+id+" telah dihapus", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        v.getContext().startActivity(intent);
                                    }
                                });
                                alertdb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog adlg = alertdb.create();
                                adlg.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });
    }

    private void HapusData(final String idx) {
        String url_update = "http://10.0.2.2/umyTI/deletetm.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCCESS = "success";
        final int[] sukses = new int[1];

        StringRequest stringreq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses[0] = jObj.getInt(TAG_SUCCESS);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : "+error.getMessage());
            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("id",idx);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreq);
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
