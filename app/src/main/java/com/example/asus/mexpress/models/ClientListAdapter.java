package com.example.asus.mexpress.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.mexpress.R;

import java.util.ArrayList;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientListViewHolder> {
    private ArrayList<Client> list;

    public ClientListAdapter(ArrayList<Client> list) {
        this.list = list;
    }

    @Override
    public ClientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_client, parent, false);
        return new ClientListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClientListViewHolder holder, int position) {
        final Client client = this.list.get(position);
        holder.txtName.setText(client.getName()+" "+ client.getLastName());
        holder.txtPhone.setText(client.getPhoneNumber()+"");
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ClientListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtPhone;

        public ClientListViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone);
        }
    }

}

