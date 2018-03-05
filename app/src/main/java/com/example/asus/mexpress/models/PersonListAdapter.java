package com.example.asus.mexpress.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.mexpress.R;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ClientListViewHolder> {
    private ArrayList<Person> list;

    public PersonListAdapter(ArrayList<Person> list) {
        this.list = list;
    }

    @Override
    public ClientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_person, parent, false);
        return new ClientListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClientListViewHolder holder, int position) {

        final Person person = this.list.get(position);
        holder.txtName.setText(person.getName() + " " + person.getLastName());
        holder.txtPhone.setText(person.getPhoneNumber() + "");
        Bitmap bmp = BitmapFactory.decodeByteArray(person.getPhoto(), 0, person.getPhoto().length);
        holder.photo.setImageBitmap(Bitmap.createScaledBitmap(bmp, 80, 90, false));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ClientListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName;
        private TextView txtPhone;
        private ImageView photo;

        public ClientListViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone);
            photo = (ImageView) itemView.findViewById(R.id.photo_in_list);
            txtName.setOnClickListener(this);
            itemView.setOnClickListener(this);
            txtName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            delete(getAdapterPosition());
        }
    }
}

